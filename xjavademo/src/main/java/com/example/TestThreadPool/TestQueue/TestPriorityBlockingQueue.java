package com.example.TestThreadPool.TestQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**PriorityBlockingQueue
 * ���ȼ�����
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
                    System.out.println("�̣߳�" + threadName + ",����ִ�����ȼ�Ϊ��" + priority + "������");
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
        �̣߳�pool-1-thread-2,����ִ�����ȼ�Ϊ��2������
        �̣߳�pool-1-thread-3,����ִ�����ȼ�Ϊ��3������
        �̣߳�pool-1-thread-1,����ִ�����ȼ�Ϊ��1������
        �̣߳�pool-1-thread-2,����ִ�����ȼ�Ϊ��10������
        �̣߳�pool-1-thread-3,����ִ�����ȼ�Ϊ��9������
        �̣߳�pool-1-thread-1,����ִ�����ȼ�Ϊ��8������
        �̣߳�pool-1-thread-1,����ִ�����ȼ�Ϊ��7������
        �̣߳�pool-1-thread-3,����ִ�����ȼ�Ϊ��6������
        �̣߳�pool-1-thread-2,����ִ�����ȼ�Ϊ��5������
        �̣߳�pool-1-thread-2,����ִ�����ȼ�Ϊ��4������
        */
