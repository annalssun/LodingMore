package com.cowinclub.dingdong.lru.recyclelru;

public interface UIUpdateCallBack {
    int getFirstVisablePosition();
    int getLastVisablePosition();
    void updataposition(int position,int itemCount);
}
