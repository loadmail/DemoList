package com.example.uploadimage.cameraUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.uploadimage.R;
import com.example.uploadimage.WebViewJavaScriptFunction;

public class CameraActivity extends Activity {
    String base64Data = "";
    private WebView mWebView;
    String baseData = "data:image/png;base64,";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @SuppressLint("JavascriptInterface")
    private void initView() {
        mWebView = (WebView) findViewById(R.id.maim_web);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl(" http://27.50.132.151:9080/test/x.html");
      //  mWebView.loadUrl("file:///android_asset/upload_image.html");

        /**添加js调用方法
         * @return
         */
        mWebView.addJavascriptInterface(new WebViewJavaScriptFunction() {
            @Override
            public void onJsFunctionCalled(String tag) {

            }

            @JavascriptInterface
            public void selectFromAndroid() {
                CameraUtils.selectImage(CameraActivity.this);
            }
            @JavascriptInterface
            public String returnData() {
                return base64Data;
            }
        }, "Android");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        CameraUtils.onActivityResult(CameraActivity.this, requestCode, resultCode, data, new CameraUtils.DataCallBack<Bitmap>() {
            @Override
            public void setData(Bitmap bitmap) {

                base64Data = baseData + CameraUtils.imgToBase64(bitmap);
                Log.e("执行", "base64Data: "+base64Data);
                mWebView.loadUrl("javascript:getImagePhoto()");
            }
        });
    }
}
