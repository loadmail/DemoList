package com.example.uploadimage.cameraUtil;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Administrator on 2016/5/24.
 */
public class MyWebViewClient extends WebViewClient {
    public MyWebViewClient() {
        super();
    }

    /**
     对网页中超链接按钮的响应。当按下某个连接时WebViewClient会调用这个方法，并传递参数：按下的url。
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);//在当前的webview中跳转到新的url
        return true;
        //启动手机浏览器来打开新的url
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(url));
//            startActivity(i);
//            return true;

    }

    /**
     WebView中的shouldOverrideUrlLoading和onPageStarted这两个方法就是可以捕获到跳转的url，然后进行一系列的操作,但是他们两到底有什么区别呢？
     当点击页面中的链接的时候他们俩都会执行，但是返回到上一个页面的时候onPageStarted会执行，但是shouldOverrideUrlLoading就不执行了，
     就是onPageStarted什么时候都执行的.

     应用场景：有个需求WebView加载不同的url的时候应用的标题也是跟着改变的，这时候只要在onPageStarted中捕获url前缀进行判断就可以了，
     但是不能使用shouldOverrideUrlLoading，因为当从当前的页面返回到上个页面的时候这个方法并不执行，所以捕获就没有用了！
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }
}
