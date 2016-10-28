package com.example.li.demo.testTouch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/4/29.
 */
public class MLayout extends LinearLayout{
    private static final String TAG = "haha";
    public MLayout(Context context) {
        super(context);
    }

    public MLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("haha","MLayout----onTouchEvent");
      return   super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("haha","MLayout----dispatchTouchEvent");
        return  super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("haha","MLayout----onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }
}
