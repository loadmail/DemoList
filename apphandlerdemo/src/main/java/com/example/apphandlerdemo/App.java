package com.example.apphandlerdemo;

import android.app.Application;
import android.os.Handler;

/**
 * Created by Administrator on 2016/5/17.
 */
public class App extends Application {
    public static final int FLUSH_MAIN_ACTIVITY = 1;
    private Handler mHandler;


    protected void setHandler(Handler handler) {
        mHandler = handler;
    }

    protected void postMessage() {
        mHandler.sendEmptyMessage(FLUSH_MAIN_ACTIVITY);
    }
}
