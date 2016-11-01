package com.example.li.demo.appHandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.example.li.demo.R;

public class HandlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        Handler handler = HandlerApp.getInstance().getHandler();
        Message msg = handler.obtainMessage();
        msg.arg1 = 1;
        msg.what = 2;
        handler.handleMessage(msg);

    }
}
