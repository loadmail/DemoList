package com.example.testjsui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ZoomButtonsController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private WebView mWebView;
    private ContactsService mContactsService;
    private final String url = "file:///android_asset/index.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.id_table_webview);//获取WebVIew
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);///一系列的初始化设置
        mWebView.getSettings().setAllowFileAccess(true);// 设置允许访问文件数据
        mWebView.getSettings().setSupportZoom(true);//支持放大网页功能
        mWebView.getSettings().setBuiltInZoomControls(true);//支持缩小网页功能
        mWebView.getSettings().setDisplayZoomControls(false);//设定缩放控件隐藏

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JSObject(), "Android");//前面对象，后面js中的调用名（我们可以看成这个JSObject类的实例是employee，用于给javascript里调用
        mWebView.setWebViewClient(new WebViewClient());//设置打开i时不用系统浏览器。使用本地WebView打开
        mWebView.loadUrl(url);
        mContactsService = new ContactsService();
    }

    private final class JSObject {
        /**
         * html中通过自定义的js 对象调用
         * 高能预警：If you've set your targetSdkVersion to 17 or higher, you must add the @JavascriptInterface
         即在一切需要在JS中调用的对象方法前加上@JavascriptInterface, 在api 17 即 Android 4.2.2 之后
         */
        @JavascriptInterface
        public void showcontacts() {

            List<Contacts> contactses = mContactsService.getContactsImf();//提供数据
            JSONArray jsonArray = new JSONArray();
            try {
                for (Contacts contact : contactses) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", contact.getId());
                    jsonObject.put("name", contact.getName());
                    jsonObject.put("phone", contact.getPhone());
                    jsonArray.put(jsonObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final String json = jsonArray.toString();
            /**
             *  A WebView method was called on thread 'JavaBridge'. All WebView methods must be called on the same thread
             *  简单来说就是html里调用java方法和java调用js方法必须在同一线程
             */
            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript:show('" + json + "')");//通过webview调用js 方法
                }
            });
        }
    }

    //反射实现放大缩小控件隐藏,不管用啊
    public void setZoomControlGone(View view) {
        Class classType;
        Field field;

        try {
            classType = WebView.class;
            field = classType.getDeclaredField("mZoomButtonsController");
            field.setAccessible(true);
            ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(view);
            mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
            try {
                field.set(view, mZoomButtonsController);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
