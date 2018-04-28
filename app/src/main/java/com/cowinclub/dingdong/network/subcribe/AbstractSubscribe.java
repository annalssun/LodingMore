package com.cowinclub.dingdong.network.subcribe;

import android.content.Context;

import com.cowinclub.dingdong.BuildConfig;
import com.cowinclub.dingdong.network.exception.ApiException;

import rx.Subscriber;

/**
 * Created by Administrator on 2018-03-27.
 */

public class AbstractSubscribe<T> extends Subscriber<T> {

    private Context mContext;

    public AbstractSubscribe(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCompleted() {

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
    public void onNext(T t) {

    }

    /**
     * 处理AP异常
     */
    public void processApiException() {

    }
}
