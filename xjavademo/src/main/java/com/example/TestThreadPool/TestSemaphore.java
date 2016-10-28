package com.example.TestThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**信号量 Semaphore
 * 释放一个才能申请一个
 * Created by Administrator on 2016/5/18.
 */
public class TestSemaphore {

    public static void main(String[] args) {
        // 线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 只能个线程同时访问
        final Semaphore semp = new Semaphore(5);
        // 模拟个客户端访问
        for (int index = 1; index < 21; index++) {
            final int NO = index;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        // 获取许可
                        semp.acquire();
                        System.out.println("Accessing: " + NO);
                        Thread.sleep((long) (Math.random() * 1000));

                        // 访问完后，释放
                        semp.release();
                        System.out.println("Release: " + NO);
                    } catch (InterruptedException e) {
                    }
                }
            };
            executorService.execute(run);
        }
        // 退出线程池
        executorService.shutdown();
    }
}

