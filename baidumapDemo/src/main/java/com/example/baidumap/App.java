package com.example.baidumap;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Administrator on 2016/5/12.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        SDKInitializer.initialize(this);
        super.onCreate();
    }

}