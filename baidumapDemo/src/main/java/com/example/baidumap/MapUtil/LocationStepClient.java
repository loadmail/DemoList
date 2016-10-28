package com.example.baidumap.MapUtil;

import android.content.Context;

/**
 * 用来控制百度地图和高德地图的共同接口
 */
public abstract class LocationStepClient {

	protected Context context;

	public LocationStepClient(Context context) {
		this.context = context;
	}

	public abstract void initLocationConfig();

	public abstract boolean isStarted();

	public abstract void start();

	public abstract void stop();

	public abstract void requestLocation();

	/**add 和LocationClient的绑定解除没有什么关系
	 * @param callback
	 */
	public abstract void registerCallbackImpl(LocationDataCallback callback);//add

	/**delete
	 * @param callback
	 */
	public abstract void unRegisterCallbackImpl(LocationDataCallback callback);//delete

	public abstract void destroy();
}
