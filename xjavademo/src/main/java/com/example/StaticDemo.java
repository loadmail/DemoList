package com.example;

import java.io.File;

/**
 * Created by Administrator on 2016/5/5.
 */
public class StaticDemo {

    public void fileList(File file) {
        if (file.isFile()) {
            //如果是文件,输出文件名
            System.out.println("文件名:" + file.getName());
        } else if (file.isDirectory()) {
            file.listFiles();
            for (File f : file.listFiles()) {
                fileList(f); //遍历
            }
        }
    }

    //定义成员变量,用于累加
long size = 0;
    public void getFileSize(File file) {
        if (file.isFile()) {
          size += file.length();

        } else if (file.isDirectory()) {
            file.listFiles();
            for (File f : file.listFiles()) {
                getFileSize(f); //遍历
            }
        }
    }
    public static void main(String[] args) {
        Test t1 = new Test();
        System.out.println(t1.count);  //1
        System.out.println(Test.count);  //1
        Test t2 = new Test();

        System.out.println(t2.count);  //2
        System.out.println(Test.count);  //2
    }
}
