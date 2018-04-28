package com.cowinclub.dingdong.network.subcribe;

/**
 * Created by Administrator on 2018-03-27.
 */

public interface onSuccessListener<T> {
    void onSeccess(T t);

    void onFailed(int code, String message);
}
