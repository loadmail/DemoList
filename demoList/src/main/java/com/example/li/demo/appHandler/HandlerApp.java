package com.example.li.demo.appHandler;

import android.app.Application;
import android.os.Handler;

/**
 * Created by ly on 2016/10/28 18:01.
 */

public class HandlerApp extends Application {
    private static HandlerApp instance;
    private  Handler mHandler = new Handler(getMainLooper());

    public static HandlerApp getInstance() {
        return instance;
    }

    public Handler getHandler() {
        return mHandler;
    }
    public void setHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }




    @Override
    public void onCreate() {
        super.onCreate();
    }

}
