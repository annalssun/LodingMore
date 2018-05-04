package com.cowinclub.dingdong.lru.recyclelru;

import android.util.LruCache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LruRecycleData<T> implements IRecycleData<T> {

    //数据缓存
    private LruCache<Integer, T> dataCache = null;

    private int count;
    private UIUpdateCallBack callBack;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    

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

    @Override
    public T get(int position) {
        return null;
    }

    @Override
    public void recycle() {

    }
}
