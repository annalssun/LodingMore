package com.cowinclub.dingdong.lru.recyclelru;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.cowinclub.dingdong.common.util.GsonFactory;
import com.cowinclub.dingdong.mdpulltorefresh.MyLog;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class DiskLruCacheManage {

    //缓存大小
    private final int DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;
    //    private Context context;
    private DiskLruCache mDiskLruCache;

    private Context context;
    private String name;

    /**
     * @param context
     * @param name    缓存路径的名字
     */
    public DiskLruCacheManage(Context context, String name) {
        this.context= context;
        this.name = name;
        openDiskCache();
    }


    private void openDiskCache() {
        try {
            File cacheDir = getDiskCacheDir(context, name);
            if (!cacheDir.exists()) {
                cacheDir.mkdir();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, DISK_CACHE_MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存的目录
     */
    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }


    /**
     * 获取版本号
     */
    private int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }


    /**
     * 将数据存储到硬盘之中
     */
    public void savePicture(String url) {
        String key = hashKeyForDisk(url);
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                if (DownLoadHttpClient.getInstance().saveData(url, outputStream)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
            }
            mDiskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    @SuppressLint("CheckResult")
    public  <T> void saveDataToDisk(T t, Class calss, final String key) {
        final String jsonStr = GsonFactory.getInstance().toJson(t, calss);

            Observable.empty()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            MyLog.i("*****************************1");
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            MyLog.i("*****************************2");
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {
                            DiskLruCache.Editor editor;
                            if (mDiskLruCache.isClosed()){
                                openDiskCache();
                            }
                            editor = mDiskLruCache.edit(key);
                            if (editor != null) {
                                OutputStream outputStream = editor.newOutputStream(0);
                                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
                                bw.write(jsonStr);
                                editor.commit();
                                mDiskLruCache.flush();
                                bw.close();
                                mDiskLruCache.flush();
                        }
                    }});



    }

    public Bitmap getPictureFromDisk(String url) {
        String key = hashKeyForDisk(url);
        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = mDiskLruCache.get(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (snapshot != null) {
            InputStream inputStream = snapshot.getInputStream(0);
            return BitmapFactory.decodeStream(inputStream);
        }
        return null;
    }

    public <T> T getDataFromDisk(String key, Class calss) {
        DiskLruCache.Snapshot snapshot = null;
        InputStreamReader reader = null;
        try {
            snapshot = mDiskLruCache.get(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (snapshot != null) {
                StringWriter writer = new StringWriter();
                InputStream inputStream = snapshot.getInputStream(0);
                reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                char[] buffer = new char[1024];
                int count;
                while ((count = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, count);
                }
                String jsonStr = writer.toString();
                writer.close();
                reader.close();
                return (T) GsonFactory.getInstance().fromJson(jsonStr, calss);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void clear(){
        try {
            mDiskLruCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
