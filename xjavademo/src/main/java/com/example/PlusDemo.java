package com.example;

/**
 * Created by Administrator on 2016/5/6.
 */
public class PlusDemo {
    public static void main(String[] args) {
        int x = 0;
        int y = 0;
        int a = x++;  //a= 0 x=1
        System.out.println(a);  //
        System.out.println(x);
        int b = ++y;   //b =1 y = 1
        System.out.println(b);
        System.out.println(y);
        int c = ++x;    //c =2 x = 2
        System.out.println(c);
        System.out.println(x);

        int d =x++ + ++x +x + x++;
        System.out.println(d);   //2 +4 +4 +4 = 14
        System.out.println(x);  // 5

    }
}
