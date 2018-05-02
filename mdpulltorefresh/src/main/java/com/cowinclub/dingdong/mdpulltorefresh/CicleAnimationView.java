package com.cowinclub.dingdong.mdpulltorefresh;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class CicleAnimationView extends FrameLayout {
    public CicleAnimationView(@NonNull Context context) {
        super(context);

    }

    public CicleAnimationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CicleAnimationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    View cicleView;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        View view = inflate(getContext(), R.layout.view_loading, null);
        cicleView = view.findViewById(R.id.cicle_iv);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        addView(view, params);
    }

    public void startAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(cicleView, "rotation", 0f, 1080f);
        animator.setDuration(2000);
        animator.setRepeatCount(10000);
        animator.start();
    }
}
