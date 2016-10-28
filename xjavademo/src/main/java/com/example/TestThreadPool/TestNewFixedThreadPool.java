package com.example.TestThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/5/18.
 */
public class TestNewFixedThreadPool {
    /**LinkedBlockingQueue<Runnable>()
     * ��ʼ�ͻ�ִ��3�����񣬶������7�����񶼻����ȴ�״̬�������߳�ִ����һ��֮��
     * �ͻ�Ӷ����а���FIFO�Ĳ���ȡ��һ���߳̽���ִ�У�
     * �ص�:���Գ���ǰ��������ʣ�µ������ǰ���˳��ִ�е�
     * @param args
     */
    public static void main(String[] args) {
         /*���ĺ����̺߳�����߳�����ȵģ�
        �����̳߳��е������̶߳��Ǻ����̣߳�
        ������Ҳ��û�г�ʱ����,
        ������������޵�����
        ���Ƕ����Ŷ�ִ��*/
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);//�ص�:�̶߳��Ǻ����߳�,�Ŷ�ִ��

        for (int i = 1; i <= 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("�߳�" + threadName + ",����:" + index);
                    try {
                        //ģ���ʱ
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
/*�߳�pool-1-thread-2,����:2
�߳�pool-1-thread-3,����:3
�߳�pool-1-thread-1,����:1
�߳�pool-1-thread-2,����:4
�߳�pool-1-thread-3,����:5
�߳�pool-1-thread-1,����:6
�߳�pool-1-thread-2,����:7
�߳�pool-1-thread-3,����:8
�߳�pool-1-thread-1,����:9
�߳�pool-1-thread-2,����:10*/
