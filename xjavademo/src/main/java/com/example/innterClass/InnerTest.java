package com.example.innterClass;

/**内部类可以用static修饰
 * 没有static修饰的内部类,不能有static方法
 *
 * static方法的返回值必须的是static修饰的. d1
 * Created by lizhichao on 16/7/6.
 */
public class InnerTest {
    public static void main(String[] args) {

    }

    private static double d1 = 1.0;

    //insert inner class declaration code here
    // TODO: 16/7/6 非static内部类不能有static声明
//    public class InnerOne {
//        public static double method() {
//            return d1;
//        }
//    }

    //todo static方法返回值是static值 d1 not static
    static class InnerOne {
         static   double method() {
            return d1;
        }
    }
    // TODO: 16/7/6 可以是抽象类 
//    abstract class InnerOne {
//        abstract double method();
//
//    }
}
