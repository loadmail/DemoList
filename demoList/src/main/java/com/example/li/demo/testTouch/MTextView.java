package com.example.li.demo.testTouch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.li.demo.AnnoCallback;
import com.example.li.demo.MainActivity;

/**
 * Created by Administrator on 2016/4/29.
 */
public class MTextView extends TextView{
    private static final String TAG = "haha";
    public MTextView(Context context) {
        super(context);
    }

    public MTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    @Override
    // TODO: 2016/6/13 仅仅是一个注解提示 
    @AnnoCallback(name = MainActivity.class)
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("haha","MTextView----onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("haha","MTextView----dispatchTouchEvent");
        return super.dispatchTouchEvent(event);

    }

}
