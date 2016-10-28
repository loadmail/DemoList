package com.example.baidumap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.zdp.aseo.content.AseoZdpAseo;

import java.io.File;

public class AdActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_ad, null);
        setContentView(view);
        initFile() ;//加载广告位置ad
    }

    /**
     * 添加广告
     * 设置图片文件夹
     */
    @SuppressLint("SdCardPath")
    private void initFile() {
        //aseo_brand中加入广告
        AseoZdpAseo.initBan(this, findViewById(R.id.aseo_brand));
        File dir = new File("/sdcard/fanxin");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
