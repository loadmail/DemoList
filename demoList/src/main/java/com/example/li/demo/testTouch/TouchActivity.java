package com.example.li.demo.testTouch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.example.li.demo.R;

/**
 * touch事件demo
 */
public class TouchActivity extends AppCompatActivity {
    private static final String TAG = "haha";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("haha","TouchActivity----onTouchEvent");

        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("haha","TouchActivity----dispatchTouchEvent");
        return  super.dispatchTouchEvent(ev);
    }

}
