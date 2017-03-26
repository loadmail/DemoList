package com.example.li.demo.qqDemo;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by lizhichao on 16/11/26.
 */

public class QQLayout extends ViewGroup {

    private View redView;
    private View blueView;
    private ViewDragHelper helper;
    private MyCallback callback;
private  Scroller scroller;

    public QQLayout(Context context) {
        super(context);
        init();
    }


    public QQLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QQLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        callback = new MyCallback();
        helper = ViewDragHelper.create(this, callback);

        scroller = new Scroller(getContext());
    }


    class MyCallback extends ViewDragHelper.Callback {
        // TODO: 16/11/27 重写方法 override method  ctrl+o  实现方法 implement method ctrl+i


        /**
         * 是否捕捉子view的触摸事件
         *
         * @param child     捕捉view
         * @param pointerId
         * @return
         */
        // TODO: 16/11/27
        @Override
        public boolean tryCaptureView(View child, int pointerId) {

            //捕捉
            return child == redView || child == blueView;
        }


        /**
         * 捕捉的解析
         *
         * @param capturedChild
         * @param activePointerId
         */
        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }



        /**
         * 水平方向的范围
         *
         * @param child
         * @return
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return super.getViewHorizontalDragRange(child);
        }


        /**
         * 修正范围
         *
         * @param child
         * @param left  left = child.getLeft()+dx;
         * @param dx    本次水平移动的距离
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int rightBounds = getMeasuredWidth() - child.getMeasuredWidth();
            if (left < 0) {
                left = 0;
            } else if (left > rightBounds) {
                left = rightBounds;
            }
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            int bottomBounds = getMeasuredHeight() - child.getMeasuredHeight();
            if (top < 0) {
                top = 0;
            } else if (top > bottomBounds) {
                top = bottomBounds;
            }
            return top;
        }



        /**
         当child位置发生改变时执行
         * @param changedView
         * @param left  child当前最新的left
         * @param top child当前最新的top
         * @param dx  本次移动的距离
         * @param dy  本次移动的距离
         */
        // TODO: 16/11/27 伴随移动
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView == redView) {
                blueView.layout(blueView.getLeft() +dx,blueView.getTop() +dy,
                        blueView.getRight()+dx,blueView.getBottom() +dy);
            } else if (changedView == blueView) {
                redView.layout(redView.getLeft()+dx,redView.getTop() +dy,
                        redView.getRight()+dx,redView.getBottom() +dy);
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            if (releasedChild.getLeft()>getMeasuredWidth()/2+redView.getMeasuredWidth()/2) {
//
                helper.smoothSlideViewTo(releasedChild,0,releasedChild.getTop());
                ViewCompat.postInvalidateOnAnimation(QQLayout.this);

                //scroller的方式不太会用
//scroller.startScroll(releasedChild.getScrollX(),releasedChild.getScrollY(),0,releasedChild.getScrollY(),1000);
//invalidate();
     //           smoothScroller();
            }
            int dragRange = getMeasuredWidth() - releasedChild.getMeasuredWidth();
            //1.计算滑动的百分比
            float fraction = releasedChild.getLeft()*1f/dragRange;
            //2.执行伴随动画
            setAnim(fraction);
        }
    }

    public void computeScroll() {
        if (helper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(QQLayout.this);
        }
//        if (scroller.computeScrollOffset()) {
//           scrollTo(scroller.getCurrX(),scroller.getCurrY());
//            invalidate();
//        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return helper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        helper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // TODO: 16/11/26 step1
        redView = getChildAt(0);
        blueView = getChildAt(1);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // TODO: 16/11/26  step2.显示子view

        // int size = getResources().getDimension(R.dimen.mWidth);  mWidth = 100dp;
        //int widthSpec = MeasureSpec.makeMeasureSpec(size,MeasureSpec.EXACTLY);

        int widthSpec = MeasureSpec.makeMeasureSpec(redView.getLayoutParams().width, MeasureSpec.EXACTLY);
        int heightSpec = MeasureSpec.makeMeasureSpec(redView.getLayoutParams().height, MeasureSpec.EXACTLY);
        redView.measure(widthSpec, heightSpec);
      //  blueView.measure(widthSpec, heightSpec);
        //还可以用以下两种来measure  如果没有特殊的测量需求  或者直接继承FrameLayout不再重写onMeasure()方法
//        measureChildren( widthMeasureSpec,  heightMeasureSpec);
        measureChild(blueView,widthMeasureSpec,heightMeasureSpec);

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        // TODO: 16/11/26 step3 确定位置
        //添加上中间位置
        int left = getPaddingLeft() + (getMeasuredWidth() - redView.getMeasuredWidth()) / 2 - getPaddingLeft();
        int top = getPaddingTop();
        redView.layout(left, top, left + redView.getMeasuredWidth(), top + redView.getMeasuredHeight());
        blueView.layout(left, redView.getBottom(), left + blueView.getMeasuredWidth(),
                top + redView.getMeasuredHeight() + blueView.getMeasuredHeight());

        redView.offsetLeftAndRight(40);//view的移动
        blueView.offsetTopAndBottom(40);
    }
// TODO: 16/11/28 移动的比率的计算 
    // TODO: 16/11/28  float fraction = view.getLeft()*1f(转化成float)/getLeft()最大的距离  getLeft随移动会不断的变化

    private void setAnim(float fraction) {
        //属性动画,用到nineoldAndroid

       ViewHelper.setScaleX(redView,1+0.5f*fraction);  //缩放动画

        //背景的过度颜色  一个工具类
    }

}
