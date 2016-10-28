package com.example.baidumap.MapUtil.impl;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.baidumap.MapUtil.LocationBean;
import com.example.baidumap.MapUtil.LocationDataCallback;
import com.example.baidumap.MapUtil.LocationStepClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * 定位核心
 * 实现BDLocationListener接口
 * 得到经纬度 地址 省份 城市 地区 等信息
 * 封装到LocationData中,
 * 将data作为LocationDataCallback接口方法参数传递
 *
 */
public class BaiduLocationImpl extends LocationStepClient implements BDLocationListener {

	public LocationClient mLocationClient = null;

	Set<LocationDataCallback> dataCallbacks;

	public BaiduLocationImpl(Context context) {
		super(context);
		dataCallbacks = new HashSet<LocationDataCallback>();
		mLocationClient = new LocationClient(context);
		mLocationClient.registerLocationListener(this);//通过this,可知调用的是本身的api
	}

	@Override
	public void initLocationConfig() {
		LocationClientOption option = new LocationClientOption();
		// 设置定位模式:Hight_Accuracy高精度、Battery_Saving低功耗、Device_Sensors仅设备(GPS)
		option.setLocationMode(LocationMode.Hight_Accuracy);
		// Finals 2016-2-25 GPS开关

		option.setOpenGps(true);
		option.disableCache(false);
		// 设置坐标类型
		option.setCoorType("bd09ll");
		// 设置定位模式10分钟定位一次，小于1秒则一次定位;大于等于1秒则定时定位
		option.setScanSpan(65 * 1000);
		// 返回的定位结果包含地址信息
		option.setIsNeedAddress(true);
		// 设置产品名称
		option.setProdName("uupaotui");
		// 是否需要 POI结果
		option.setIsNeedLocationPoiList(false);
		// 是否需要位置描述信息
		option.setIsNeedLocationDescribe(false);
		// 是否允许模拟位置
		option.setEnableSimulateGps(false);
		// 是否需要返回设备的方位
		option.setNeedDeviceDirect(false);
		mLocationClient.setLocOption(option);
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
			mLocationClient.start();
		}
	}

	@Override
	public void requestLocation() {
		if (mLocationClient != null) {
			mLocationClient.requestLocation();
		}
	}

	@Override
	public void stop() {
		if (mLocationClient != null) {
			mLocationClient.stop();
		}
	}
public LocationDataCallback callback;



	@Override
	public void onReceiveLocation(BDLocation location) {
		LocationBean data = null;
		if (location != null) {
			data = new LocationBean(location.getLatitude(), location.getLongitude(), location.getCity(), location.getAddrStr(), location.getDistrict(), location.getProvince(), location.getLocType());
			callback.getLocationData(data);
		}
		Iterator<LocationDataCallback> iterable = dataCallbacks.iterator();
		List<LocationDataCallback> callbacks = new ArrayList<LocationDataCallback>();
		synchronized (iterable) {
			while (iterable.hasNext()) {
				LocationDataCallback item = iterable.next();
				if (item != null) {
					callbacks.add(item);
				}
			}
		}
		for (int i = 0; i < callbacks.size(); i++) {
			LocationDataCallback callback = callbacks.get(i);
			if (callback != null) {
				callback.getLocationData(data);  //关键
			}
		}
	}

	public void registerCallbackImpl(LocationDataCallback listener) {
		dataCallbacks.add(listener);
	}

	public void unRegisterCallbackImpl(LocationDataCallback listener) {


		Iterator<LocationDataCallback> iterable = dataCallbacks.iterator();
		synchronized (iterable) {
			while (iterable.hasNext()) {
				LocationDataCallback item = iterable.next();
				if (item == listener) {
					iterable.remove();
					break;
				}
			}
		}
	}

	@Override
	public void destroy() {
		if (mLocationClient != null) {
			mLocationClient.unRegisterLocationListener(this);
		}
		if (isStarted()) {
			stop();
		}
	}

}
