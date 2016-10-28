package com.example.TestThreadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * newScheduledThreadPool
 * DelayedWorkQueue�������������Ե�ִ�У�Ҳ����˵���̳߳ؿ��������Ե�ִ������
 * �ӳ�4���ÿ��2��ִ��һ�θ�����
 */
public class TestScheduledThreadPool {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
        System.out.println("����ʼ");
        //�ӳ�4���ÿ��1��ִ��һ�θ�����
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("ִ������"+System.currentTimeMillis()/1000);
            }
        }, 4, 2, TimeUnit.SECONDS);//�ؼ���
    }
}
