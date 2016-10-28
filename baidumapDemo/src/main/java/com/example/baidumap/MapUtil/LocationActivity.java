package com.example.baidumap.MapUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.baidumap.R;

public class LocationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        //开启定位服务,显示notification
        Intent intent = new Intent(LocationActivity.this, LocationService.class);
        startService(intent);
    }
}
