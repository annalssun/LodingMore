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

public class MDFooterView extends FrameLayout {
    TextView textView;

    public MDFooterView(@NonNull Context context) {
        super(context);
        textView = new TextView(getContext());
    }

    public MDFooterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MDFooterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        textView.setText("加载中");
        textView.setBackgroundColor(Color.BLUE);
        textView.setGravity(Gravity.CENTER);
        FrameLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        textView.setVisibility(VISIBLE);
        addView(textView, layoutParams);
        setBackgroundColor(Color.TRANSPARENT);
    }


    public void setChildVisibility(boolean isShow) {
        if (isShow) {
            textView.setVisibility(VISIBLE);
        } else {
            textView.setVisibility(GONE);
        }
    }
}
