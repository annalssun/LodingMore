package com.cowinclub.dingdong.mdpulltorefresh;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;

public class MDHeaderView extends FrameLayout {

    public MDHeaderView(@NonNull Context context) {
        super(context);
    }

    public MDHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MDHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();


        FrameLayout.LayoutParams layoutParams = new LayoutParams(Util.dip2px(getContext(),100), Util.dip2px(getContext(),20));
        layoutParams.gravity = Gravity.CENTER;
//        addView(progressBar, layoutParams);
        setBackgroundColor(Color.TRANSPARENT);
    }

    public void startAnimation() {

    }

    public void endAnimation() {

    }
}
