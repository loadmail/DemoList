package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Administrator on 2016/5/11.
 */
public class StreamDemo {
    public static void main(String[] args) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        //创建字节输出流
        try {
//            File file  = new File("e:\\a.txt");
//            String path = file.getAbsolutePath();
            fis = new FileInputStream("e:\\a.txt"); //必须先在指定目录下创建文件
            //利用InputStreamReader包装FileInputStream字节流,并指定编码为gbk
            isr = new InputStreamReader(fis,"utf-8");
            //利用bufferReader包装isr
            br = new BufferedReader(isr);
            //读取文件中的一行字符
            String line = br.readLine();
            System.out.println(line);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭对象
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        FileOutputStream fos = null ;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;

        try {
            fos = new FileOutputStream("E:\\a.txt");
            osw = new OutputStreamWriter(fos,"gbk");
            bw = new BufferedWriter(osw);
            bw.write("七年毛哥");
        } catch (Exception e) {

        }finally {
            if (bw != null ) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (osw != null ) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fos != null ) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
