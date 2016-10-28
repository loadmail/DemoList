package com.finals.gpsprovider.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;

public class SimulateLocationManager implements LocationListener, Runnable {

	private String mMockProviderName = LocationManager.GPS_PROVIDER;

	LocationManager mLocationManager;

	LatLng currentLatLng;

	boolean isRun = true;

	public SimulateLocationManager(Context context) {
		mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		try {
			mLocationManager.addTestProvider(mMockProviderName, false, true, false, false, true, true, true, 0, 5);
			mLocationManager.setTestProviderEnabled(mMockProviderName, true);
			mLocationManager.requestLocationUpdates(mMockProviderName, 0, 0, this);
		} catch (Exception e) {
			Toast.makeText(context, "模拟位置权限被禁用，请开启", Toast.LENGTH_SHORT).show();
		}
		new Thread(this).start();
	}

	/**
	 * setLocation 设置GPS的位置
	 * 
	 */

	@SuppressLint("NewApi")
	public void setLocation(LatLng mLatLng) {
		this.currentLatLng = Util.bd_decrypt(mLatLng.latitude, mLatLng.longitude);
	}

	public void StopLocation() {
		currentLatLng = null;
	}

	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onProviderDisabled(String location) {

	}

	@Override
	public void onProviderEnabled(String location) {

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

	}

	@SuppressLint("NewApi")
	@Override
	public void run() {
		while (isRun) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (currentLatLng != null) {
				Location location = new Location(mMockProviderName);
				location.setTime(System.currentTimeMillis());
				location.setLatitude(currentLatLng.latitude);
				location.setLongitude(currentLatLng.longitude);
				location.setAltitude(2.0f);
				location.setAccuracy(3.0f);
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
					long elaspedTime = SystemClock.elapsedRealtimeNanos();
					location.setElapsedRealtimeNanos(elaspedTime);
				}
				try {
					mLocationManager.setTestProviderLocation(mMockProviderName, location);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void onDestroy() {
		StopLocation();
		isRun = false;
	}

}
