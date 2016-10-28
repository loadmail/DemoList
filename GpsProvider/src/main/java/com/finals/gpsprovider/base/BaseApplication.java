package com.finals.gpsprovider.base;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

public class BaseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		SDKInitializer.initialize(this);
	}

}
