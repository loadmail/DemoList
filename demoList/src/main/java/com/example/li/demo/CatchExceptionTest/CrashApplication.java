package com.example.li.demo.CatchExceptionTest;

import android.app.Application;

/**
 * Created by lizhichao on 16/7/7.
 */
public class CrashApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        CrashHandler catchHandler = CrashHandler.getInstance();
//        catchHandler.init(getApplicationContext());
    }
}
