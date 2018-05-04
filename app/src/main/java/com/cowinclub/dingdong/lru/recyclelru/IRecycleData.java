package com.cowinclub.dingdong.lru.recyclelru;

public interface IRecycleData<T> {
    int DATA_MAXSIZE = 30;

    //和RecycleView主要方法对应
    void onBindViewHolder(int position);

    void onViewAttachedToWindow(int position);

    void onViewDetachedFromWindow(int position);

    void onViewRecycled(int position);

    //获取总数目
    int getItemCount();

    //设置总数目
    void setItemCount(int count);


    //回收内存
    void recycle();
}
