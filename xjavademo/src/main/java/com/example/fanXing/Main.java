package com.example.fanXing;

/**
 * Created by ly on 2016/10/20 12:03.
 */

public class Main {

    public static <T> void out(T... args) {
        for (T t :
                args) {
            System.out.println(t);
        }

    }

    public static void main(String[] args) {

        Container<String, String> c1 = new Container<String, String>("name", "hansheng");
        Container<String, Integer> c2 = new Container<String, Integer>("age", 22);
        Container<Double, Double> c3 = new Container<Double, Double>(1.1, 1.3);
        System.out.println(c1.getKey() + " : " + c1.getValue());
        System.out.println(c2.getKey() + " : " + c2.getValue());
        System.out.println(c3.getKey() + " : " + c3.getValue());

        out(c1.getKey());
    }



}
