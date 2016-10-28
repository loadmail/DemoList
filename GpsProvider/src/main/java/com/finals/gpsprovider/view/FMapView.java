package com.finals.gpsprovider.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.finals.gpsprovider.R;

/**
 * 把MapView和BaiduMap封装起来
 */
public class FMapView extends LinearLayout {

    View rootView;

    BaiduMap mBaiduMap;

    MapView mMapView;

    /**
     * 添加rootView 也可以添加其他控件
     * MapView和BaiduMap的初始化
     * 移除不要控件
     * mBaiduMap缩放初始化
     *
     * @param context
     * @param attrs
     */
    public FMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            rootView = LayoutInflater.from(context).inflate(R.layout.f_baidumap, null);
            this.addView(rootView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mMapView = (MapView) findViewById(R.id.f_baidumapview);
        } catch (Exception e) {

        }
        if (mMapView != null) {
            mMapView.removeViewAt(2);// 移除缩放控件
            mMapView.removeViewAt(1);

            mBaiduMap = mMapView.getMap();
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            mBaiduMap.setMyLocationEnabled(true);
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(17));
        }
    }

    /**
     * 设置监听
     * 代替mBaiduMap.setCallBack(callback)
     *
     * @param callback
     */
    public void setOnMapLoadedCallback(OnMapLoadedCallback callback) {
        if (callback != null) {
            if (mBaiduMap != null) {
                mBaiduMap.setOnMapLoadedCallback(callback);
            }
        }
    }

    public void setOnMapStatusChangeListener(OnMapStatusChangeListener listener) {
        if (listener != null && mBaiduMap != null) {
            mBaiduMap.setOnMapStatusChangeListener(listener);
        }
    }

    /**
     * 把这些方法封装在这个类中
     */
    public void onPause() {
        if (mMapView != null) {
            mMapView.onPause();
        }
    }

    public void onResume() {
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    public void clear() {
        if (mBaiduMap != null) {
            mBaiduMap.clear();
        }
    }

    public void onDestroy() {
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }

    /**
     * 设置定位
     * 就是调用mBaiduMap.setMapStatus()定位位置
     *
     * @param latLng
     */
    public void setLocation(LatLng latLng) {
        if (latLng == null) {
            return;
        }
        if (mBaiduMap != null) {
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(latLng));
        }
    }

    /**
     * 设置位置和对应的图标
     * option = new MarkerOptions().position(latLng).icon(bitmap);
     * mBaiduMap.addOverlay(option);
     *
     * @param latLng
     * @param icon
     */
    public void addBitmapDescriptor(LatLng latLng, int icon) {
        if (latLng == null) {
            return;
        }
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(icon);
        OverlayOptions option = new MarkerOptions().position(latLng).icon(bitmap);
        if (mBaiduMap != null) {
            mBaiduMap.addOverlay(option);
        }
    }


}
