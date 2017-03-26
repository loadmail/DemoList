package com.example.sellTickets;

/**
 * 卖票程序
 * Created by lizhichao on 16/12/14.
 */

public class TicketTest {
    public static void main(String[] args) {

//二进制  八进制  16进制
        //6的二进制  110  60的二进制 111100
        //-6的二进制


        //char的小知识点

        char p = 'a';
        System.out.println(p + 1);  //98

        System.out.println((char) (p + 1));  //b

//a++ 与 ++a  考虑赋值和运算的先后
        int a = 3;
        int b = 3;
        int c;
        c = a++;
        System.out.println("c:" + c + "--a:" + a); //c:3--a:4

        c = ++b;
        System.out.println("c:" + c + "--b:" + b);  // c:4--b:4


//运算时的数据类型问题
        int x = 2740;
        System.out.println("值是:---" + x / 1000 * 1000);   //结果: 2000
        System.out.println("值是:---" + x / 1000f * 1000);  //结果: 2740.0


// 类型提升报错
//        byte t = 5;
//        t = t + 3;    //一个字节的byte装不下4个字节的int


        //异常体系
        //  Throwable 包括 Error 和 Exception;
        //error举例: 虚拟机解决不了的问题  如加载不存在的类文件  申请超出虚拟机内存的内存
        //exception举例: a/b  b=0


        //算术运算符
        System.out.println("5+5=" + 5 + 5);   //5+5=55
        System.out.println("5+5=" + (5 + 5));  //  5+5=10


        short b1 = 5;
        // b1 = b1 + 4; //自动提升为int
        b1 += 4;  //没有问题  直接赋值

        //逻辑运算符

        //^异或符号
        //两边相同为false  两边不同为true
        System.out.println(true ^ true);   //false
        System.out.println(true ^ false);  //true

        //位运算符
        System.out.println(3 << 2);   //3 * 2(2) = 12   左移
        System.out.println(6 >> 1);    //  6 / 2(1) =3  右移
        System.out.println(6 >> 2);    //  6 / 2(2) = 6 / 4 = 1

        System.out.println(6 & 3);  //2
        System.out.println(6 | 3);  //7
        System.out.println(6 ^ 3);  //5

        System.out.println(7 ^ 4 ^ 4);   //7 数据加密


        //交换参数位置
        //不用多余变量的方式

        int m = 4;
        int n = 3;

        n = m ^ n;
        m = m ^ n;
        n = m ^ n;
// int<255  数值过大损失精度
//        n = m+n;
//        m = n-m;
//        n = n-m;
        System.out.println("m=" + m + ",n=" + n);


        //  线程与进程

        // 线程是控制单元,执行路径  进程是内存分配的内存空间

        //jvm 有两个线程  主线程和垃圾回收线程

        //同步的前提是多线程

        //懒汉式单例 考点: 延迟加载 多线程有安全问题  同步方法  低效(多次判断同步)  双重判断  锁是class

        //死锁  同步嵌套同步,而锁不同  死锁代码,停止运行

        //多线程通信:  同一数据 一存一取


        //票数用的是static
//        TicketsThread t1 = new TicketsThread();
//        TicketsThread t2 = new TicketsThread();
//        TicketsThread t3 = new TicketsThread();
//        TicketsThread t4 = new TicketsThread();

//        t1.start();
//        t2.start();
//        t3.start();
//        t4.start();



        /*
        * Exception in thread "main" java.lang.IllegalThreadStateException
	at java.lang.Thread.start(Thread.java:705)
	at com.example.sellTickets.TicketTest.main(TicketTest.java:21)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at com.intellij.rt.execution.application.AppMain.main(AppMain.java:144)
线程状态从开始状态到运行状态,重复start会报异常
        * */
//        t1.start();
//        t1.start();
//        t1.start();
//        t1.start();

        // TODO: 16/12/14 资源的独立共享 没有用static
        TicketRunnable runnable = new TicketRunnable();

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);
        Thread t4 = new Thread(runnable);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
//        new Thread(runnable).start();
//        new Thread(runnable).start();
//        new Thread(runnable).start();
//        new Thread(runnable).start();

        /*
        * 继承和实现的比较:
        *
        * 以多线程为例,
        * 学生类可能需要多线程,就需要继承Thread,但更重要的是要extends Person  就需实现Runnable接口
        *
        *
        *
        * */


        // TODO: 16/12/14  多线程的安全问题
    }


    // TODO: 16/12/17 同步会频繁判断锁,双重判断会增加效率
    static class Single {


        public static Single instance;

        private Single() {

        }

        public static Single getInstance() {
            if (instance == null)
            {
                synchronized (Single.class)
                {
                    if (instance == null)
                    {
                        instance = new Single();
                    }
                }
            }
            return instance;
        }
    }
}