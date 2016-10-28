package com.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/5/6.
 */
public class ArrayDemo {
    public static void main(String[] args) {
        String[][] arr = {{"aa", "bb", "cc"}, {"aa1", "bb1", "cc1"}, {"aa2", "bb2", "cc2"}, {"aa3", "bb3", "cc3"}, {"aa4", "bb4", "cc4"}};

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {//真坑  把j都写成i了,半天出不来
                System.out.println(arr[i][j]);
            }
            System.out.println();
        }

        try {
            File.createTempFile("pqw", "haha");
        } catch (IOException e) {
            e.printStackTrace();

        }
        Set<String> sets = new HashSet<String>();
        List<String> lists = new ArrayList<>();
        Iterator<String> iterator = sets.iterator();
        synchronized (iterator) {
            while (iterator.hasNext()) {
                String item = iterator.next();
                lists.add(item);
            }
        }


    }
}
