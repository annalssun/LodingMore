package com.cowinclub.dingdong.view;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cowinclub.dingdong.R;

public class ProgressDialog extends Dialog {

    private Context mContext;
    private View cicleView;

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

        View view = View.inflate(mContext, R.layout.dialog_loading, null);
        cicleView = view.findViewById(R.id.cicle_iv);
        setContentView(view);
        WindowManager m = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.4
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);

        setCanceledOnTouchOutside(false);
//        startAnimation();

    }

    @Override
    public void show() {
        super.show();
        startAnimation();
    }

    public void startAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(cicleView, "rotation", 0f, 1080f);
        animator.setDuration(2000);
        animator.setRepeatCount(10000);
        animator.start();
    }
}
