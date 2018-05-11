package com.cowinclub.dingdong.lru.recyclelru;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.cowinclub.dingdong.bean.TestBean;
import com.cowinclub.dingdong.mdpulltorefresh.MyLog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LruRecycleData implements IRecycleData {

    int BITMAP_MAXSIZE = 8;
    int DATA_MAXSIZE = 30;
    int mPage = 30;

    private LruCache<Integer, TestBean.Data> testBeanDataCache = null;
    //图片缓存
    private LruCache<String, Bitmap> pictureCache = null;


    private int mItemCount;
    private UIUpdateCallBack callBack;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private DiskLruCacheManage diskLruCacheManage;

    public LruRecycleData(Context context, String specialName, UIUpdateCallBack callBack) {
        mItemCount = 0;
        pictureCache = new LruCache<>(BITMAP_MAXSIZE);
        testBeanDataCache = new LruCache<>(DATA_MAXSIZE);
        diskLruCacheManage = new DiskLruCacheManage(context, specialName);
        this.callBack = callBack;
    }

    @Override
    public void onBindViewHolder(int position) {
        loadDataToCache(position);
    }

    @Override
    public void onViewAttachedToWindow(int position) {
        loadDataToCache(position);
    }

    @Override
    public void onViewDetachedFromWindow(int position) {

    }

    @Override
    public void onViewRecycled(int position) {

    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    @Override
    public void setItemCount(int count) {

    }

    public TestBean.Data get(int position) {
        return testBeanDataCache.get(position);
    }

    @Override
    public void recycle() {

    }

    public void loadBitmap(final String url) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                String key = hashKeyForDisk(url);
                Bitmap bitmap = pictureCache.get(key);
                if (bitmap == null) {
                    bitmap = diskLruCacheManage.getPictureFromDisk(url);
                    if (bitmap != null) {
                        pictureCache.put(key, bitmap);
                    }
                }
            }
        });
    }


    public void saveDataToCache(final TestBean bean, final int page) {
        MyLog.i("***************************进来");
        if (testBeanDataCache == null || bean == null) return;

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                if (page == 1) {
                    mItemCount = 0;
                    testBeanDataCache.evictAll();
                    MyLog.i("数量***********1****************" + testBeanDataCache.putCount());
                    MyLog.i("数量***********2****************" + testBeanDataCache.createCount());
                    diskLruCacheManage.clear();
                }
                int cacheLength = mItemCount;
                int size = bean.getData().size();
                mPage = size == 0 ? 30 : bean.getData().size();
                for (int i = 0; i < size; i++) {
                    testBeanDataCache.put(cacheLength + i, bean.getData().get(i));
                    mItemCount = mItemCount + 1;
                }
                diskLruCacheManage.saveDataToDisk(bean, TestBean.class, page + "");
                if (page == 1) {
                    callBack.updataposition(0, 0);
                } else {
                    callBack.updataposition(cacheLength-2, size+2);
                }

            }
        });
    }


    public void loadDataToCache(final int pos) {
        if (testBeanDataCache.get(pos) != null) return;
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                int currentPage = pos / mPage + 1;
                TestBean bean = diskLruCacheManage.getDataFromDisk(currentPage + "", TestBean.class);
                if (bean != null) { //已经缓存到本地
                    int startPos = currentPage == 1 ? 0 : (currentPage - 1) * mPage - 1; //获取添加的位置
                    for (int i = startPos; i < startPos + bean.getData().size(); i++) {
                        testBeanDataCache.put(i, bean.getData().get(i));
                        mItemCount = mItemCount + 1;
                    }
                    callBack.updataposition(pos,mPage);
                }
            }
        });

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
