package com.cowinclub.dingdong.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.cowinclub.dingdong.R;

public class ProgressDialog extends Dialog {

    private Context mContext;

    public ProgressDialog(@NonNull Context context) {
        this(context, R.style.WaitingDialogTheme);
    }

    public ProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
