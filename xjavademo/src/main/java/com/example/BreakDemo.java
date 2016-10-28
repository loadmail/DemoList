package com.example;

/**
 * Created by Administrator on 2016/5/6.
 */
public class BreakDemo {
    public static void main(String[] args) {
        for (int i = 0; i < 6; i++) {
            if (i == 3) {
//                break;
//  continue;
                return;
            }
            System.out.println(i);
        }
        System.out.println("Game Over");
    }

}
