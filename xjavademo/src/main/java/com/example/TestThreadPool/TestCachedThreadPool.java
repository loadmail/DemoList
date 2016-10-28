package com.example.TestThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/5/18.
 */
public class TestCachedThreadPool {

    /**当有存活的空闲线程的时候，任务会放到该空闲线程中去执行例如任务2和任务1就是在同一个线程中去执行的，
     * 而如果没有空闲线程的话，新提交的任务就会开启一个新的线程来执行该任务
     *
     *  这类线程池比较适合执行大量的耗时少的任务，
     * 当线程池处于闲置状态的时候，
     * 线程池中的线程都会被销毁，
     *  这个时候该线程池几乎是不占用任何系统资源的
     */
    public static void main(String[] args) {
       /* newCachedThreadPool
        可以无限的创建线程
        当新任务向线程池中提交的时候，如果有空闲线程，就会把任务放到空闲线程中去，如果没有空闲线程，就会开启一个新的线程来执行此任务
        它的队列SynchronousQueue是一个特殊的队列，在多数情况下，我们可以把它简单的理解为一个无法插入的队列
        */

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 10; i++) {
            final int index = i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("线程：" + threadName + ",任务:" + index);
                    try {
                        long time = index * 500;
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
/*
线程：pool-1-thread-1,任务:1
线程：pool-1-thread-1,任务:2
线程：pool-1-thread-2,任务:3
线程：pool-1-thread-1,任务:4
线程：pool-1-thread-2,任务:5
线程：pool-1-thread-3,任务:6
线程：pool-1-thread-1,任务:7
线程：pool-1-thread-2,任务:8
线程：pool-1-thread-4,任务:9
线程：pool-1-thread-3,任务:10
*/