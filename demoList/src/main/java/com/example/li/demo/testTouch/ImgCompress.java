package com.example.li.demo.testTouch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by ly on 2016/10/25 16:37.
 */

public class ImgCompress {

    public static void main(String[] args) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile("path",options);
        double ratio = Math.max(options.outHeight*1.0d/1024f,options.outWidth*1.0d/1024f);// TODO: 2016/10/26 图片不超过3m
        options.inSampleSize = (int) Math.ceil(ratio);  // TODO: 2016/10/26 取整
        options.inJustDecodeBounds = false;
        BitmapFactory.decodeFile("path",options);
    }


    public byte[] getBitmapByte(Bitmap bitmap){
        //将bitmap压缩到内存中 转换成字节数组
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }


    public Bitmap getBitmapFromByte(byte[] temp){
        if(temp != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        }else{
            return null;
        }
    }
}
