package com.cowinclub.dingdong.mdpulltorefresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

public class CicleProgressBar extends View {
    private int DEFAULT_SIZE; //默认空间大小


    private int mCurrentProgress = 0;

    private String showContent = "正在加载";

    private int progress = 100;
    private int maxProgress = 100;
    private Paint mCirclePaint;
    private Paint mArcPaint;
    private Paint mLargeCirclePaint;
    private Paint mTextPaint;


    private int mWidth;
    private int mHeight;

    private int mRadius;
    private int mStrokeWidth;

    private int currentProgress = 0;

    private int textBaseLine;

    public CicleProgressBar(Context context) {
        super(context);
        init(context);
    }

    public CicleProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CicleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mStrokeWidth = Util.dip2px(context, 2);
        DEFAULT_SIZE = Util.dip2px(context, 8); //默认空间大小
        initPaint();
    }


    //初始化画笔
    private void initPaint() {

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(Color.RED);
        mArcPaint.setStrokeWidth(mStrokeWidth);
        mArcPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(30);
        mTextPaint.setTextAlign(Paint.Align.LEFT);



    }

    float centerX;
    float centerY;
    float radius;
    float startAngle = 0;
    float sweepAngle;
    RectF rectF;

    private void measure() {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int width = mWidth - paddingLeft - paddingRight;
        int height = mHeight - paddingBottom - paddingTop;



        //半径=长、宽最小值/2-边界线的宽度
        radius = Math.min(width, height) / 2 - mStrokeWidth;
        centerX = paddingLeft + radius;
        centerY = paddingTop + height / 2;
        sweepAngle = (float) (300);
        rectF = new RectF(mStrokeWidth, centerY - radius, centerX + radius, centerY + radius);

        // 设置字体在中间
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(showContent, 0, showContent.length(), bounds);
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        textBaseLine = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        startAngle = (float) (360 * (currentProgress / 100.00)) * 2;
        /**画外侧圆*/
        canvas.drawArc(rectF, startAngle, -sweepAngle, false, mArcPaint);
        canvas.drawText(showContent, 5*radius,textBaseLine, mTextPaint);

        canvas.drawColor(Color.TRANSPARENT);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getViewSize(widthMeasureSpec);
        mHeight = getViewSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
        measure();
    }

    private int getViewSize(int measureSpec) {
        int size = DEFAULT_SIZE;
        int mode = MeasureSpec.getMode(measureSpec);
        int modeSize = MeasureSpec.getSize(measureSpec);
        return mode == MeasureSpec.AT_MOST || mode == MeasureSpec.EXACTLY ? modeSize : size;
    }

    public void start() {
//        AnimatorSet animationSet = new AnimatorSet();
//        animationSet.play(animationToCircle());
//        animationSet.start();
//        runInt();


    }

    private ValueAnimator animationToCircle() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentProgress = (int) valueAnimator.getAnimatedValue();
                if (currentProgress == 100 && listener != null) {
                    listener.complete();
                }
                invalidate();
            }
        });
        return animator;
    }



    private void runInt() {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 96);
        valueAnimator.setDuration(10000);
        valueAnimator.setInterpolator(new AccelerateInterpolator(1));
        valueAnimator
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        //设置瞬时的数据值到界面上
                        mCurrentProgress = (int) valueAnimator.getAnimatedValue();
                        if (mCurrentProgress == 100 && listener != null) {
                            listener.complete();
                        }
                    }
                });
        valueAnimator.start();
    }


    private onCompleteListener listener;

    public void setOnCompleteListener(onCompleteListener listener) {
        this.listener = listener;
    }

    public interface onCompleteListener {
        void complete();
    }
}
