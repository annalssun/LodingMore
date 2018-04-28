package com.cowinclub.dingdong.network.subcribe;


import android.app.Dialog;
import android.os.Handler;
import android.os.Message;

public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 0;
    public static final int DISMISS_PROGRESS_DIALOG = 1;

    private Dialog mProgressDialog;

    public ProgressDialogHandler(Dialog progressDialog) {
        this.mProgressDialog = progressDialog;
    }

    private void showProgressDialog() {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void handleMessage(Message msg) {
//        super.handleMessage(msg);
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                showProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                hideProgressDialog();
                break;
        }
    }
}
