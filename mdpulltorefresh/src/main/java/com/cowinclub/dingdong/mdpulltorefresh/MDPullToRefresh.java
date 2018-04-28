package com.cowinclub.dingdong.mdpulltorefresh;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

public class MDPullToRefresh extends FrameLayout {

    private final static int DEFAULT_WAVE_HEIGHT = 130;
    private final static int DEFAULT_HEADER_HEIGHT = 100;
    private final static int DEFAULT_FOOTER_HEIGHT = 30;

    private int mHeaderHeight;
    private int mFooterHeight;
    private int mWaveHeight;

    private View mChildView;
    private MDHeaderView mHeaderView;
    private MDFooterView mFooterView;

    private float mTouchY;
    private float mCurrentY;
    private float mOffsetY; //用于恢复到原始位置

    private boolean isLoadMore = false;

    private boolean isRefreshing = false;
    private boolean isLoadingMore = false;

    //控制下拉过程中动画效果
    private DecelerateInterpolator mDecelerateInterpolator;


    public MDPullToRefresh(Context context) {
        this(context, null);
    }

    public MDPullToRefresh(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MDPullToRefresh(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);

    }

    /**
     * 初始化属性值进行默认值设置
     */
    private void initView(@Nullable AttributeSet attrs) {
        mHeaderHeight = DEFAULT_HEADER_HEIGHT;
        mFooterHeight = DEFAULT_HEADER_HEIGHT;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        Context context = getContext();

        mChildView = getChildAt(0);
        if (null == mChildView) return;

        mDecelerateInterpolator = new DecelerateInterpolator(10);
        mWaveHeight = Util.dip2px(context, DEFAULT_WAVE_HEIGHT);
        mHeaderView = new MDHeaderView(context);
        LayoutParams headerParams = new LayoutParams(LayoutParams.MATCH_PARENT, Util.dip2px(context, 0));
        headerParams.gravity = Gravity.TOP;
        mHeaderView.setLayoutParams(headerParams);
        mHeaderView.setVisibility(GONE);
        setHeaderView(mHeaderView);

        mFooterView = new MDFooterView(context);
        LayoutParams footerParams = new LayoutParams(LayoutParams.MATCH_PARENT, Util.dip2px(context, 0));
        footerParams.gravity = Gravity.BOTTOM;
        mFooterView.setLayoutParams(footerParams);
        mFooterView.setVisibility(GONE);
        setFooterView(mFooterView);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float currentY = ev.getY();
                float dy = currentY - mTouchY;
                if (dy > 0 && canScrollDown()) {
                    mHeaderView.setVisibility(VISIBLE);
                    return true;
                } else if (dy < 0 && canScrollUp()) {
                    mFooterView.setVisibility(VISIBLE);
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mCurrentY = event.getY();
                float dy = mCurrentY - mTouchY;
                if (mChildView != null) {
                    if (dy > 0) {
                        dy = Math.min(mWaveHeight * 2, dy);
                        dy = Math.max(0, dy);
                        mOffsetY = mDecelerateInterpolator.getInterpolation(dy / mWaveHeight / 2) * dy / 2;
                        mHeaderView.getLayoutParams().height = isRefreshing ? (int) mOffsetY + mHeaderHeight : (int) mOffsetY;
                        mChildView.setTranslationY(isRefreshing ? (int) mOffsetY + mHeaderHeight : (int) mOffsetY);
                        mHeaderView.requestLayout();
                    } else if (dy < 0) {
                        float loadMoreDy = -dy;
                        loadMoreDy = Math.min(mWaveHeight * 2, loadMoreDy);
                        loadMoreDy = Math.max(0, loadMoreDy);
                        mOffsetY = mDecelerateInterpolator.getInterpolation(loadMoreDy / mWaveHeight / 2) * loadMoreDy / 2;
                        mOffsetY = mOffsetY > mFooterHeight * 2 ? mFooterHeight * 2 : mOffsetY;
                        mFooterView.getLayoutParams().height = isLoadingMore ? (int) mOffsetY + mFooterHeight : (int) mOffsetY;
                        mChildView.setTranslationY(-(isLoadingMore ? (int) mOffsetY + mFooterHeight : (int) mOffsetY));
                        mFooterView.requestLayout();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mCurrentY = event.getY();
                float actionUpDy = mCurrentY - mTouchY;
                if (null != mChildView) {
                    if (actionUpDy > 0) {
                        actionUpDy = isRefreshing ? mCurrentY - mTouchY + mHeaderHeight : mCurrentY - mTouchY;
                        if (actionUpDy >= mHeaderHeight) {
                            createAnimationTranslationY(mOffsetY, mHeaderHeight, mChildView, mHeaderView);
                            isRefreshing = true;
                        } else {
                            createAnimationTranslationY(mOffsetY, 0, mChildView, mHeaderView);
                        }
                    } else if (actionUpDy < 0) {
                        actionUpDy = isRefreshing ? mCurrentY - mTouchY - mFooterHeight : mCurrentY - mTouchY;
                        actionUpDy = -actionUpDy;
                        if (actionUpDy >= mFooterHeight) {
                            createAnimationTranslationY(-mOffsetY, -mFooterHeight, mChildView, mFooterView);
                            isLoadingMore = true;
                        } else {
                            createAnimationTranslationY(-mOffsetY, 0, mChildView, mHeaderView);
                        }
                    } //
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    private void createAnimationTranslationY(float start, float end, final View view, final FrameLayout frameLayout) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", start, end);
        animator.setDuration(1000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int translationY = (int) view.getTranslationY();
                frameLayout.getLayoutParams().height = Math.abs(translationY);
                frameLayout.requestLayout();
            }
        });
        animator.start();
    }


    private boolean canScrollUp() {
        if (mChildView.canScrollVertically(-1)) {
            MyLog.i("***************************可以加载更多");
        } else {
            MyLog.i("***************************不可以加载更多");
        }
        return !mChildView.canScrollVertically(-1);
    }

    private boolean canScrollDown() {
        if (mChildView.canScrollVertically(1)) {
            MyLog.i("***************************下拉刷新");
        } else {
            MyLog.i("***************************不可以下拉刷新");
        }
        return !mChildView.canScrollVertically(1);
    }

    /**
     * 添加下拉刷新头部
     */
    private void setHeaderView(View view) {
        if (null == view) return;
        addView(view);
    }

    /***/
    private void setFooterView(View view) {
        if (null == view) return;
        addView(view);
    }


}
