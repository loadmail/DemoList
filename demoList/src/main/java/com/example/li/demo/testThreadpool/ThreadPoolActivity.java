package com.example.li.demo.testThreadpool;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.li.demo.R;

public class ThreadPoolActivity extends AppCompatActivity implements View.OnClickListener {
    Button FIFOBtn, LIFOBtn;
    TextView mainTv;
    private MyThreadPool myThreadPool;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);


        FIFOBtn = (Button) findViewById(R.id.btn_FIFO);
        LIFOBtn = (Button) findViewById(R.id.btn_LIFO);
        mainTv = (TextView) findViewById(R.id.tv);

        FIFOBtn.setOnClickListener(this);
        LIFOBtn.setOnClickListener(this);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                //设置给TextView;
                mainTv.setText("任务:" + msg.what);
                return false;
            }
        });
//确定线程池核心线程数的时候通常会根据CPU的核心数来确定的，通常会使用CPU核心数+1来定为当前线程池的核心线程数
        //默认是1
        myThreadPool = new MyThreadPool(Runtime.getRuntime().availableProcessors()+1 );


        for (int i = 0; i < 100; i++) {
            final int index = i;
            myThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(index);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_FIFO:
                myThreadPool.setWay(MyThreadPool.OutWay.FIFO);
                break;
            case R.id.btn_LIFO:
                myThreadPool.setWay(MyThreadPool.OutWay.LIFO);
                break;
        }
    }

}

