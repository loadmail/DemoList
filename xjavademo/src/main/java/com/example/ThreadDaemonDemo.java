package com.example;

/**DaemonThread被设置为守护线程
 * Created by Administrator on 2016/5/13.
 */
public class ThreadDaemonDemo extends Thread {
    public static void main(String[] args) {
ThreadDaemonDemo dt = new ThreadDaemonDemo();
        dt.setDaemon(true);
        dt.start();
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("---DaemonThread--"+i);
        }
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("---CurrentThread--"+i);
        }
    }
}
