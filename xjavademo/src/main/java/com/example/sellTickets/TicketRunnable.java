package com.example.sellTickets;

/**
 * 多线程的安全问题:
 * <p>
 * 多条语句操作共享数据时,一个线程只对语句执行了一部分,还没有执行完,另一条线程就参与进来执行,导致共享数据的错误.
 * <p>
 * <p>
 * 解决方案:
 * 多条对共享数据操作的语句,让一个线程都执行完,才让其他线程参与.
 * 同步代码块
 * Created by lizhichao on 16/12/14.
 */

public class TicketRunnable implements Runnable {
    // TODO: 16/12/14 注意  这里没有static ,资源可以被独立共享
    private int ticket = 1000;  //ticket是共享数据,这是多线程安全问题的关键

    Object obj = new Object();

    @Override
    public void run() {
//        while (true) {
//           synchronized (obj) {
//
//
//                if (ticket > 0) {
//                    try {
//                        Thread.sleep(10);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    // TODO: 16/12/14 i++的赋值问题,上句是  被先赋值,后运算
//                    System.out.println(currentThread().getName() + "卖出第" + ticket-- + "张票");
//                    // TODO: 16/12/14 ++i i先运算,后赋值
//                    // System.out.println(currentThread().getName()+"卖出第"+ --ticket +"张票");
//              }
//            }
//        }

//
//
//

        // TODO: 16/12/14 这样子的代码完全切换不了线程,为什么?
//synchronized (obj){  //在这里不能达到线程的切换,
//        while (ticket > 0) {
//          //  synchronized (obj) {在这里不能达到同步的效果
//                try {
//                    Thread.sleep(10);  //todo 加上sleep,会出现0 -1 -2,多线程出现安全问题
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                // TODO: 16/12/14 i++的赋值问题,上句是  被先赋值,后运算
//                System.out.println(currentThread().getName() + "卖出第" + ticket-- + "张票");
//
//                // TODO: 16/12/14 ++i i先运算,后赋值
//                // System.out.println(currentThread().getName()+"卖出第"+ --ticket +"张票");
//
//            }
//        }
    }
}
