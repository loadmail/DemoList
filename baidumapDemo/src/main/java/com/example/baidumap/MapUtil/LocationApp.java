package com.example.baidumap.MapUtil;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.example.baidumap.MapUtil.impl.BaiduLocationImpl;

/**
 * 获取当前定位信息
 * 作为app公用变量
 * 经纬度 地址 城市 ...
 */
public class LocationApp extends Application {

    // 定位
    public LocationStepClient mLocationClient = null;
    public CallBackImpl dataImpl = new CallBackImpl();
    //定位数据
    public double lat = 0.0;
    public double lng = 0.0;
    public String city = "";
    public String county = "";
    public String address = "";

    @Override
    public void onCreate() {

        super.onCreate();
        SDKInitializer.initialize(this);
        initBaiduLocation();
    }

    /**
     * 初始化定位配置
     */
    public void initBaiduLocation() {
        if (mLocationClient == null) {
            mLocationClient = new BaiduLocationImpl(this);
            mLocationClient.initLocationConfig();
        }
        mLocationClient.registerCallbackImpl(dataImpl);
    }

    /**
     * 启动定位,开始定位
     */
    // TODO: 2016/6/2 线程调用LocationThread
    // TODO: 2016/6/2  service开启线程 LocationService
    // TODO: 2016/6/2  LoginAsyn开启service
    public void startBaiduLocation() {
        if (mLocationClient != null) {
            if (!mLocationClient.isStarted()) {
                mLocationClient.start();
            }
        }
    }

    /**
     * 清理定位资源
     */
    public void clearLocationClient() {
        if (mLocationClient != null) {
            mLocationClient.unRegisterCallbackImpl(dataImpl);
        }
        if (mLocationClient != null) {
            mLocationClient.destroy();
        }
        mLocationClient = null;
    }

    public class CallBackImpl implements LocationDataCallback {
        @Override
        public void getLocationData(LocationBean location) {
            if (location == null)
                return;
            if (location.getLatitude() == 4.9E-324 || location.getLongitude() == 4.9E-324) {
                return;
            }
            if (location.getProvince() != null && !location.getProvince().equals("")) {
                lat = location.getLatitude();
                lng = location.getLongitude();
                address = location.getAddrStr();
                city = location.getCity();
                county = location.getDistrict();
            }
        }
    }
}
