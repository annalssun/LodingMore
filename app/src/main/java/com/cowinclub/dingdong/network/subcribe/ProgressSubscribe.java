package com.cowinclub.dingdong.network.subcribe;

import android.content.Context;

/**
 * Created by Administrator on 2018-03-27.
 */

public class ProgressSubscribe<T> extends AbstractSubscribe<T> {
    private boolean isShowProgressBar = false;
    private Context mContext;
    private onSuccessListener listener;

    public ProgressSubscribe(Context context, onSuccessListener listener) {
        this(context, listener, false);
    }

    public ProgressSubscribe(Context context, onSuccessListener listener, boolean isShowProgressBar) {
        super(context);
        this.mContext = context;
        this.listener = listener;
        this.isShowProgressBar = isShowProgressBar;
    }

    @Override
    public void onCompleted() {
        super.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
    }

    @Override
    public void onNext(T t) {
        super.onNext(t);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
