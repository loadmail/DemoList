package com.example.li.demo.appHandler;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

/**
 * Created by ly on 2016/10/28 18:01.
 */

public class HandlerApp extends Application {
    private static HandlerApp instance;


    private  Handler mHandler = new Handler(getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    public static HandlerApp getInstance() {
        return instance;
    }

    public Handler getHandler() {
        return mHandler;
    }
    public void setHandler(Handler mHandler) {
        this.mHandler = mHandler;
        Message msg = Message.obtain();
        msg.what = 1;
        mHandler.sendMessage(msg);

        //最终调用queue.enqueueMessage(msg, uptimeMillis);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
