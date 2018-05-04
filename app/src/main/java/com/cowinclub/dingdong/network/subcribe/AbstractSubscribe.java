package com.cowinclub.dingdong.network.subcribe;

import android.content.Context;

import com.cowinclub.dingdong.BuildConfig;
import com.cowinclub.dingdong.network.exception.ApiException;

import io.reactivex.subscribers.DisposableSubscriber;


/**
 * Created by Administrator on 2018-03-27.
 */

public class AbstractSubscribe<T> extends DisposableSubscriber<T> {

    private Context mContext;

    public AbstractSubscribe(Context context) {
        this.mContext = context;
    }


    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            processApiException();
        } else {
            if (BuildConfig.ERROR_LOG) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(T t) {

    }



    /**
     * 处理AP异常
     */
    public void processApiException() {

    }

}
