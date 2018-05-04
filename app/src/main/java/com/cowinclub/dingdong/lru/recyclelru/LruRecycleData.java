package com.cowinclub.dingdong.lru.recyclelru;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.cowinclub.dingdong.bean.TestBean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LruRecycleData implements IRecycleData {

    int BITMAP_MAXSIZE = 8;
    int DATA_MAXSIZE = 30;
    //数据缓存
    private LruCache<String, TestBean> dataCache = null;
    //图片缓存
    private LruCache<String, Bitmap> pictureCache = null;


    private int count;
    private UIUpdateCallBack callBack;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private DiskLruCacheManage diskLruCacheManage;

    public LruRecycleData(Context context, String specialName, UIUpdateCallBack callBack) {
        dataCache = new LruCache<>(DATA_MAXSIZE);
        pictureCache = new LruCache<>(BITMAP_MAXSIZE);
        diskLruCacheManage = new DiskLruCacheManage(context, specialName);
    }

    @Override
    public void onBindViewHolder(int position) {

    }

    @Override
    public void onViewAttachedToWindow(int position) {

    }

    @Override
    public void onViewDetachedFromWindow(int position) {

    }

    @Override
    public void onViewRecycled(int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void setItemCount(int count) {

    }

    public TestBean get(int position) {
        return null;
    }

    @Override
    public void recycle() {

    }

    public Bitmap getBitmap(String url) {
        String key = hashKeyForDisk(url);
        Bitmap bitmap = pictureCache.get(key);
        if (bitmap == null) {
            bitmap = diskLruCacheManage.getPictureFromDisk(url);
            if (bitmap != null) {
                pictureCache.put(key, bitmap);
            }
        }
        return bitmap;
    }

    public TestBean getData(String page) {
        TestBean testBean = dataCache.get(page);
        if (testBean == null) {
            testBean = diskLruCacheManage.getDataFromDisk(page, TestBean.class);
            if (testBean != null) {
                dataCache.put(page, testBean);
            }
        }
        return testBean;
    }

    public void saveData(TestBean bean, int page) {
        if (bean == null) return;
        diskLruCacheManage.saveDataToDisk(bean, TestBean.class, page + "");
        dataCache.put(page + "", bean);
        for (TestBean.Data data : bean.getData()) {
            diskLruCacheManage.savePicture(data.profile_image);
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
}
