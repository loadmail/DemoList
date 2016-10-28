package com.example.TestThreadPool.TestQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**PriorityBlockingQueue
 * 优先级队列
 * Created by Administrator on 2016/5/18.
 */
public class TestPriorityBlockingQueue {

    public static void main(String[] args) {
        ExecutorService priorityThreadPool = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>());

        for (int i = 1; i <= 10; i++) {
            final int priority = i;
            priorityThreadPool.execute(new PriorityRunnable(priority) {

                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("线程：" + threadName + ",正在执行优先级为：" + priority + "的任务");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}

   /*
        线程：pool-1-thread-2,正在执行优先级为：2的任务
        线程：pool-1-thread-3,正在执行优先级为：3的任务
        线程：pool-1-thread-1,正在执行优先级为：1的任务
        线程：pool-1-thread-2,正在执行优先级为：10的任务
        线程：pool-1-thread-3,正在执行优先级为：9的任务
        线程：pool-1-thread-1,正在执行优先级为：8的任务
        线程：pool-1-thread-1,正在执行优先级为：7的任务
        线程：pool-1-thread-3,正在执行优先级为：6的任务
        线程：pool-1-thread-2,正在执行优先级为：5的任务
        线程：pool-1-thread-2,正在执行优先级为：4的任务
        */
