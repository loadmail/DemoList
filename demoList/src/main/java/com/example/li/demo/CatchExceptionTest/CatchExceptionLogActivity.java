package com.example.li.demo.CatchExceptionTest;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.li.demo.R;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CatchExceptionLogActivity extends AppCompatActivity {
    private String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch_exception);


// TODO: 16/7/7 邮件发送通知
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {//给主线程设置一个处理运行时异常的handler

            @Override
            public void uncaughtException(Thread thread, final Throwable ex) {

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);

                StringBuilder sb = new StringBuilder();

                sb.append("Version code is ");
                sb.append(Build.VERSION.SDK_INT + "\n");//设备的Android版本号
                sb.append("Model is ");
                sb.append(Build.MODEL + "\n");//设备型号
                sb.append(sw.toString());

                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(Uri.parse("mailto:csdn@csdn.com"));//发送邮件异常到csdn@csdn.com邮箱
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "bug report");//邮件主题
                sendIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());//堆栈信息
                startActivity(sendIntent);
                finish();
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer a = null;
                a.toString();//触发nullpointer运行时错误
                System.out.println(s.equals("hello"));  // s没有进行赋值，所以会出现NullPointException异常

            }
        });
    }
}