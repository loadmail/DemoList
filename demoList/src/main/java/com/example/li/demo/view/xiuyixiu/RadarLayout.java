package com.example.li.demo.view.xiuyixiu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by ly on 2016/10/17 09:27.
 * 内部嵌套一个 RadarView 用来画圆
 */

public class RadarLayout extends FrameLayout {

    public static final int INFINITE = 0;

    private static final int DEFAULT_COUNT = 4;
    private static final int DEFAULT_COLOR = Color.rgb(0, 116, 193);
    private static final int DEFAULT_DURATION = 7000;
    private static final int DEFAULT_REPEAT = INFINITE;
    private static final int DEFAULT_STROKE_WIDTH = 2;

    private int mCount;
    private int mDuration;
    private int mRepeat;

    private AnimatorSet mAnimatorSet;

    private Paint mPaint;
    private int mColor;
    private float mRadius;
    private float mCenterX;
    private float mCenterY;
    private int mStrokeWidth;
    private boolean mIsStarted;
    private boolean mUseRing;

    public RadarLayout(Context context) {
        super(context);
        initGlobalParams();
    }

    public RadarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGlobalParams();
    }

    public RadarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGlobalParams();
    }

    /**
     * 添加配置
     */
    private void initGlobalParams() {
        mColor = DEFAULT_COLOR;
        mCount = DEFAULT_COUNT;
        mDuration = DEFAULT_DURATION;
        mRepeat = DEFAULT_REPEAT;
        mUseRing = false;
        mStrokeWidth = dip2px(DEFAULT_STROKE_WIDTH);
//绘制view,添加动画
        build();
    }

    /**
     * 调用开启动画
     */
    public synchronized void start() {
        if (mAnimatorSet == null || mIsStarted) {
            return;
        }
        mAnimatorSet.start();
    }

    public synchronized void stop() {
        if (mAnimatorSet == null || !mIsStarted) {
            return;
        }
        mAnimatorSet.end();
    }

    public synchronized boolean isStarted() {
        return (mAnimatorSet != null && mIsStarted);
    }

    public int getCount() {
        return mCount;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }

        if (count != mCount) {
            mCount = count;
            reset();
            invalidate();
        }
    }

    public void setDuration(int millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }

        if (millis != mDuration) {
            mDuration = millis;
            reset();
            invalidate();
        }
    }

    public void setColor(int color) {
        if (mColor != color) {
            mColor = color;
            reset();
            invalidate();
        }
    }

    public void setUseRing(boolean useRing) {
        if (mUseRing != useRing) {
            mUseRing = useRing;
            reset();
            invalidate();
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

        // 确定圆的圆点坐标及半径
        mCenterX = width * 0.5f;
        mCenterY = height * 0.5f;
        mRadius = Math.min(width, height) * 0.5f;
    }

    private void clear() {
        stop();
        removeAllViews();
    }

    /**
     * 绘制view,添加动画
     */
    private void build() {

        LayoutParams params = new LayoutParams(MATCH_PARENT, MATCH_PARENT);

        int repeatCount = (mRepeat == INFINITE) ? ObjectAnimator.INFINITE : mRepeat;

        List<Animator> animators = new ArrayList<Animator>();
        for (int index = 0; index < mCount; index++) {
            RadarView radarView = new RadarView(getContext());
            radarView.setScaleX(0);
            radarView.setScaleY(0);
            radarView.setAlpha(1);

            addView(radarView, index, params);

            // 计算时间间隔
            long delay = index * mDuration / mCount;

            // 属性动画
            animators.add(create(radarView, "scaleX", repeatCount, delay, 0, 1));
            animators.add(create(radarView, "scaleY", repeatCount, delay, 0, 1));
            animators.add(create(radarView, "alpha", repeatCount, delay, 1, 0));
        }

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(animators);
        mAnimatorSet.setInterpolator(new LinearInterpolator());
        mAnimatorSet.setDuration(mDuration);
        mAnimatorSet.addListener(mAnimatorListener);
    }

    private ObjectAnimator create(View target, String propertyName, int repeatCount, long delay, float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, propertyName, from, to);
        animator.setRepeatCount(repeatCount);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.setStartDelay(delay);
        return animator;
    }

    private void reset() {
        boolean isStarted = isStarted();

        clear();
        build();

        if (isStarted) {
            start();
        }
    }

    /**
     * 内部类
     */
    private class RadarView extends View {

        public RadarView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (null == mPaint) {
                mPaint = new Paint();
                mPaint.setColor(mColor);
                mPaint.setAntiAlias(true);
                // 注意Style的用法，【STROKE：画环】【FILL：画圆】
                mPaint.setStyle(mUseRing ? Paint.Style.STROKE : Paint.Style.FILL);
                mPaint.setStrokeWidth(mUseRing ? mStrokeWidth : 0);
            }

            // 画圆或环
            // TODO: 2016/10/17 其实就是不停的画半径不同的圆
            canvas.drawCircle(mCenterX, mCenterY, mUseRing ? mRadius - mStrokeWidth : mRadius, mPaint);
        }
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private final Animator.AnimatorListener mAnimatorListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animator) {
            mIsStarted = true;
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            mIsStarted = false;
        }

        @Override
        public void onAnimationCancel(Animator animator) {
            mIsStarted = false;
        }

        @Override
        public void onAnimationRepeat(Animator animator) {
        }
    };
}