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

    CicleAnimationView mCicleAnimationView;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();


        mCicleAnimationView = new CicleAnimationView(getContext());
        FrameLayout.LayoutParams layoutParams = new LayoutParams(Util.dip2px(getContext(), 100), Util.dip2px(getContext(), 50));
        layoutParams.gravity = Gravity.CENTER;
        addView(mCicleAnimationView, layoutParams);
        mCicleAnimationView.setVisibility(VISIBLE);
        setBackgroundColor(Color.TRANSPARENT);
    }

    public void startAnimation() {
        mCicleAnimationView.startAnimation();
    }

    public void endAnimation() {
//        mCicleAnimationView.setVisibility(GONE);
        mCicleAnimationView.endAnimation();
    }
}
