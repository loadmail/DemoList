package com.example.baidumap.MapUtil.impl;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;
import com.example.baidumap.MapUtil.LocationBean;
import com.example.baidumap.MapUtil.LocationDataCallback;
import com.example.baidumap.MapUtil.LocationStepClient;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class GaoDeLocationImpl extends LocationStepClient implements AMapLocationListener {

	AMapLocationClient mLocationClient = null;

	AMapLocationClientOption option = null;

	Set<LocationDataCallback> listeners;

	public GaoDeLocationImpl(Context context) {
		super(context);
		listeners = new HashSet<LocationDataCallback>();
		mLocationClient = new AMapLocationClient(context);
		mLocationClient.setLocationListener(this);
	}

	@Override
	public void initLocationConfig() {
		option = new AMapLocationClientOption();
		option.setLocationMode(AMapLocationMode.Hight_Accuracy);
		option.setOnceLocation(false);
		option.setInterval(60*1000);
		option.setNeedAddress(true);
		option.setMockEnable(false);
		option.setWifiActiveScan(true);
		option.setGpsFirst(true);
		mLocationClient.setLocationOption(option);
	}

	@Override
	public boolean isStarted() {
		if (mLocationClient != null) {
			return mLocationClient.isStarted();
		}
		return false;
	}

	@Override
	public void start() {
		if (mLocationClient != null) {
			mLocationClient.startLocation();
		}
	}

	@Override
	public void stop() {
		if (mLocationClient != null) {
			mLocationClient.stopLocation();
		}
	}

	@Override
	public void requestLocation() {

	}

	@Override
	public void registerCallbackImpl(LocationDataCallback listener) {
		listeners.add(listener);
	}

	@Override
	public void unRegisterCallbackImpl(LocationDataCallback listener) {
		listeners.remove(listener);
	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		LocationBean fbdLocationBean = null;
		if (location != null && location.getErrorCode() == 0) {
			// 将google地图、soso地图、aliyun地图、mapabc地图和amap地图// 所用坐标转换成百度坐标
			com.baidu.mapapi.utils.CoordinateConverter converter = new com.baidu.mapapi.utils.CoordinateConverter();
			converter.from(CoordType.COMMON);  //converter.from(CoordType.GPS);
			converter.coord(new LatLng(location.getLatitude(), location.getLongitude()));
			LatLng resuLatLng = converter.convert();

			fbdLocationBean = new LocationBean(resuLatLng.latitude, resuLatLng.longitude, location.getCity(), location.getAddress(), location.getDistrict(), location.getProvince(), location.getErrorCode());
		}
		Iterator<LocationDataCallback> iterable = listeners.iterator();
		while (iterable.hasNext()) {
			LocationDataCallback item = iterable.next();
			if (item != null) {
				item.getLocationData(fbdLocationBean);
			}
		}
	}

	@Override
	public void destroy() {
		if (isStarted()) {
			stop();
		}
		if (mLocationClient != null) {
			mLocationClient.setLocationListener(null);
			mLocationClient.onDestroy();
		}
	}

}
