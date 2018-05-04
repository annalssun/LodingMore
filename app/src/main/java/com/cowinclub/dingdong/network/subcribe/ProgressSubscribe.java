package com.cowinclub.dingdong.network.subcribe;

import android.content.Context;

import com.cowinclub.dingdong.view.ProgressDialog;

/**
 * Created by Administrator on 2018-03-27.
 */

public class ProgressSubscribe<T> extends AbstractSubscribe<T> {
    private boolean isShowProgressBar = false;
    private Context mContext;
    private onSuccessListener<T> listener;
    private ProgressDialogHandler dialogHandler;

    public ProgressSubscribe(Context context, onSuccessListener<T> listener) {
        this(context, listener, true);
    }

    public ProgressSubscribe(Context context, onSuccessListener<T> listener, boolean isShowProgressBar) {
        super(context);
        this.mContext = context;
        this.listener = listener;
        this.isShowProgressBar = isShowProgressBar;
        dialogHandler = new ProgressDialogHandler(new ProgressDialog(context));
    }

    @Override
    public void onComplete() {
        super.onComplete();
        if (isShowProgressBar && dialogHandler != null) {
            dialogHandler.sendEmptyMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG);
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);

        if (isShowProgressBar && dialogHandler != null) {
            dialogHandler.sendEmptyMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG);
        }
        if (listener != null) {
            listener.onFailed(500, e.getMessage());
        }
    }

    @Override
    public void onNext(T t) {
        if (t != null && listener != null) {
            listener.onSeccess(t);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isShowProgressBar && dialogHandler != null) {
            dialogHandler.sendEmptyMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG);
        }
    }
}
