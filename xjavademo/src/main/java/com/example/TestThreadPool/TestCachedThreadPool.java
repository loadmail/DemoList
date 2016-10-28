package com.example.TestThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/5/18.
 */
public class TestCachedThreadPool {

    /**���д��Ŀ����̵߳�ʱ�������ŵ��ÿ����߳���ȥִ����������2������1������ͬһ���߳���ȥִ�еģ�
     * �����û�п����̵߳Ļ������ύ������ͻῪ��һ���µ��߳���ִ�и�����
     *
     *  �����̳߳رȽ��ʺ�ִ�д����ĺ�ʱ�ٵ�����
     * ���̳߳ش�������״̬��ʱ��
     * �̳߳��е��̶߳��ᱻ���٣�
     *  ���ʱ����̳߳ؼ����ǲ�ռ���κ�ϵͳ��Դ��
     */
    public static void main(String[] args) {
       /* newCachedThreadPool
        �������޵Ĵ����߳�
        �����������̳߳����ύ��ʱ������п����̣߳��ͻ������ŵ������߳���ȥ�����û�п����̣߳��ͻῪ��һ���µ��߳���ִ�д�����
        ���Ķ���SynchronousQueue��һ������Ķ��У��ڶ�������£����ǿ��԰����򵥵����Ϊһ���޷�����Ķ���
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
                    System.out.println("�̣߳�" + threadName + ",����:" + index);
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
�̣߳�pool-1-thread-1,����:1
�̣߳�pool-1-thread-1,����:2
�̣߳�pool-1-thread-2,����:3
�̣߳�pool-1-thread-1,����:4
�̣߳�pool-1-thread-2,����:5
�̣߳�pool-1-thread-3,����:6
�̣߳�pool-1-thread-1,����:7
�̣߳�pool-1-thread-2,����:8
�̣߳�pool-1-thread-4,����:9
�̣߳�pool-1-thread-3,����:10
*/