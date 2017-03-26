package com.example.sellTickets;

/**
 * Created by lizhichao on 16/12/14.
 */

public class TicketsThread extends Thread{

private static int num = 100;

    @Override
    public void run() {
    super.run();
        while (num >0) {
            System.out.println(currentThread().getName()+"卖出第"+num+"张票");
            num--;
        }

    }
}
