package com.example.li.demo.testThreadpool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/5/18.
 */
public class MyThreadPool extends ThreadPoolExecutor {

    private volatile Semaphore semaphore;
    private List<Runnable> runnableList;
    private LoopThread loopThread;
    private boolean flag;

    //两种策略,先进先出和先进后出
    enum OutWay {
        FIFO, LIFO
    }

    private OutWay outWay;


    public MyThreadPool(int corePoolSize) {

        // super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        //核心线程和最大线程都是一样的
        super(corePoolSize, corePoolSize, 0l, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        semaphore = new Semaphore(corePoolSize);//信号量的数值也设置为核心线程数
        runnableList = new LinkedList<>();//List用来存放我们提交的任务
        flag = true;
        outWay = OutWay.FIFO;//默认是先进先出
        loopThread = new LoopThread();
        loopThread.start();
    }
    //提交任务的方法
    @Override
    public synchronized void execute(Runnable command) {
        //所有来的任务是提交到我们自己的任务队列中
        runnableList.add(command);
        if (runnableList.size() < 2) {
            //如果这是队列中的第一个任务,那么就去唤醒轮询线程
            synchronized (loopThread) {
                loopThread.notify();
            }

        }
    }

    //设置是FIFO/LIFO
    public void setWay(OutWay outWay) {
        this.outWay = outWay;
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        //任务完成释放信号量
        semaphore.release();
    }

    @Override
    protected void terminated() {
        super.terminated();
        flag = false;//轮询线程关闭
    }
    /**
     * 我发现这种嵌套真的很难看
     */
    class LoopThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (flag) {
                if (runnableList.size() == 0) {
                    try {
                        //如果没有任务,轮询线程就等待
                        synchronized (this) {
                            wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        //请求信号量
                        semaphore.acquire();
                        int index = runnableList.size();
                        switch (outWay) {
                            case FIFO:
                                //先进先出
                                index = 0;
                                break;
                            case LIFO:
                                //先进后出
                                index = runnableList.size() - 1;
                                break;
                        }
                        //调用父类的添加方法,将任务添加到线程池中
                        MyThreadPool.super.execute(runnableList.get(index));
                       runnableList.remove(index);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
