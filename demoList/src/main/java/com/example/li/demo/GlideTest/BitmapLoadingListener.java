package com.example.li.demo.GlideTest;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface BitmapLoadingListener {
    void onSuccess(Bitmap b);
    void onError();
}
