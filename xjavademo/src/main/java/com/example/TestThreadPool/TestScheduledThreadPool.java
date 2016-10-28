package com.example.TestThreadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * newScheduledThreadPool
 * DelayedWorkQueue能让任务周期性的执行，也就是说该线程池可以周期性的执行任务
 * 延迟4秒后，每隔2秒执行一次该任务
 */
public class TestScheduledThreadPool {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
        System.out.println("任务开始");
        //延迟4秒后，每隔1秒执行一次该任务
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行任务"+System.currentTimeMillis()/1000);
            }
        }, 4, 2, TimeUnit.SECONDS);//关键点
    }
}
