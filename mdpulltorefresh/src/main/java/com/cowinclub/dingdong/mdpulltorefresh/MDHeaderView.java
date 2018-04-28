package com.cowinclub.dingdong.mdpulltorefresh;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

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

        TextView textView = new TextView(getContext());
        textView.setText("下拉刷新");
        textView.setBackgroundColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        FrameLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        addView(textView, layoutParams);
        setBackgroundColor(Color.TRANSPARENT);
    }

    public void startAnimation(){

    }

    public void endAnimation(){

    }
}
