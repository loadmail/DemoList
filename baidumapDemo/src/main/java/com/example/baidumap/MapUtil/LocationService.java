package com.example.baidumap.MapUtil;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.baidumap.R;

/**
 * 定位服务
 * 开启上传线程
 * 开启Notification
 */
// Finals 2015-7-27 提交位置信息
public class LocationService extends Service {

	public static final String ACTION = "com.example.baidumap.LocationService";

	Notification mNotification;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	LocationThread mLocationThread;

	@Override
	public void onCreate() {
		super.onCreate();
		mLocationThread = new LocationThread(this, 100, 60000);
		mLocationThread.start();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		notification();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		if (mLocationThread != null) {
			mLocationThread.StopUpload();
		}
		try {
			stopForeground(true);
		} catch (Exception e) {

		}
		super.onDestroy();
	}

	public void notification() {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this.getApplicationContext());
		mBuilder.setSmallIcon(R.drawable.ic_launcher);
		mBuilder.setContentTitle("定位进程");
		mBuilder.setContentText("就这样默默定位吧!");

		Intent notificationIntent = new Intent();
		ComponentName component = new ComponentName(this.getPackageName(), LocationActivity.class.getName());
		notificationIntent.setComponent(component);
		PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), -2014, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		mBuilder.setContentIntent(pendingIntent);
		mNotification = mBuilder.build();

		startForeground(10086, mNotification);
	}

}
