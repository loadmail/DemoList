package com.example.fanXing;

/**调用:
 *
 String[] names = {"aa", "bb", "cc"};
 String middle = ArrayAlg.getMiddle(names);
 * Created by ly on 2016/10/20 13:04.
 */

public class ArrayAlg {
    // TODO: 2016/10/20 所谓泛型方法，就是带有类型参数的方法，它既可以定义在泛型类中，也可以定义在普通类中。
    public static <T> T getMiddle(T[] a) {
        return a[a.length / 2];
    }

    public void abc () {
        String[] names = {"aa", "bb", "cc"};
        String middle = ArrayAlg.getMiddle(names); // TODO: 2016/10/20 规定传入的是数组
    }
}

