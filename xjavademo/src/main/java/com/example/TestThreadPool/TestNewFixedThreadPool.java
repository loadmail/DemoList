package com.example.TestThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/5/18.
 */
public class TestNewFixedThreadPool {
    /**LinkedBlockingQueue<Runnable>()
     * 开始就会执行3个任务，而后面的7个任务都会进入等待状态当核心线程执行完一个之后，
     * 就会从队列中按照FIFO的策略取出一个线程进行执行，
     * 重点:所以除了前三个任务，剩下的任务是按照顺序执行的
     * @param args
     */
    public static void main(String[] args) {
         /*它的核心线程和最大线程是相等的，
        即该线程池中的所有线程都是核心线程，
        所以它也并没有超时机制,
        可以添加无上限的任务，
        但是都会排队执行*/
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);//特点:线程都是核心线程,排队执行

        for (int i = 1; i <= 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("线程" + threadName + ",任务:" + index);
                    try {
                        //模拟耗时
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
/*线程pool-1-thread-2,任务:2
线程pool-1-thread-3,任务:3
线程pool-1-thread-1,任务:1
线程pool-1-thread-2,任务:4
线程pool-1-thread-3,任务:5
线程pool-1-thread-1,任务:6
线程pool-1-thread-2,任务:7
线程pool-1-thread-3,任务:8
线程pool-1-thread-1,任务:9
线程pool-1-thread-2,任务:10*/
