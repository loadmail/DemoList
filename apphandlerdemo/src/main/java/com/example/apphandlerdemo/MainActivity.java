package com.example.apphandlerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 把handler做成App变量
 * 这种方式可以用于动态改变上一界面状态
 * 如动态更新新闻列表
 */
public class MainActivity extends AppCompatActivity {
    private App app;
    private Button button;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (App) getApplication();

        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));//启动另一个Activity
            }
        });

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case App.FLUSH_MAIN_ACTIVITY:
                        //在这可进行想要的操作
                        operat();
                        break;
                    default:
                        break;
                }

                super.handleMessage(msg);
            }
        };

        app.setHandler(handler);

    }

    private void operat() {
        ((TextView) findViewById(R.id.tv)).setText("改变");
    }
}
