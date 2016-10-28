package com.example.baidumap.MapUtil;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class LocationThread extends Thread {

	Context context;

	boolean isStart = true;

	int sleepTime;

	long deny;

	Handler handler;

	LocationApp app;

	public LocationThread(Context context, long deny, int sleepTime) {
		this.context = context;
		this.deny = deny;
		this.sleepTime = sleepTime;
		handler = new Handler(Looper.getMainLooper());
		app = (LocationApp) context.getApplicationContext();
	}

	/**
	 * 睡眠60秒,上传位置
	 *  判断百度进程是否挂了
	 */
	@Override
	public void run() {
		try {
			Thread.sleep(deny);
		} catch (Exception e1) {

		}
		while (isStart) {
			long startTime = System.currentTimeMillis();
			uploadLocation();
			// Finals 判断百度进程是否挂了
			handler.post(mRunnable);
			long duration = System.currentTimeMillis() - startTime;

			long sleep = sleepTime - duration;
			if (sleep < 0) {
				sleep = 1;
			}
			// 休眠
			try {
				Thread.sleep(sleep);
			} catch (Exception e) {

			}
		}
	}

	/**
	 * 停止上传定位 service调用
	 */
	public void StopUpload() {
		isStart = false;
	}

	private void uploadLocation() {
		try {
			// TODO: 2016/6/2 上传位置信息
		} catch (Exception e) {

		}
	}

	/**
	 * 更新位置信息
	 */
	Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			if (app != null && isStart) {
				if (app.mLocationClient == null) {
					app.initBaiduLocation();
				} else {
					app.startBaiduLocation();
				}
			}
		}
	};
}
