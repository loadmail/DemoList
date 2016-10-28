package com.example.uploadimage.cameraUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016/5/26.
 */
public class CompressUtil {
    public static final int Max_SIZE = 600;//图片的最大值

    /** 图片压缩
     * 效果一般
     */
    public static Bitmap compressImageFromFile(String srcPath) {
        Bitmap bitmap;
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//
        float ww = 480f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率


        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        bitmap2File(bitmap);
        //      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        //其实是无效的,大家尽管尝试
        return bitmap;
    }
    /**
     * bitmap保存为file
     *
     * @param bitmap
     */
    public static void bitmap2File( Bitmap bitmap) {
        // 处理bitmap对象
        OutputStream b = null;
        try {
            b = new FileOutputStream(CameraUtils.imgPath);//这里就创建了文件
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件  需要输出流 存储文件
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();  //空指针  没有读写权限的问题
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 较好的压缩
     *
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static Bitmap compressImage(String path) throws Exception {
        // TODO: 2016/5/26 这些代码还需要研究一下
        Bitmap compressBitmap = null;
        if ((getFileSizes(path) / 1024) > 100) {//大于100kb
            compressBitmap = getSmallBitmap(path, -1, -1);
            /**
             * 把图片旋转为正的方向
             */
            if (compressBitmap.getWidth() > 480) {
                compressBitmap = MaxBitMap(compressBitmap, false);
            }
            //newPath是在path最后一个分割符前加上user_photo1
            String newPath = path.substring(0, path.lastIndexOf("/") + 1) + "user_photo1" + path.substring(path.lastIndexOf("."), path.length());
            saveBmpToFile( compressBitmap, newPath);
            return compressBitmap;
        } else {
            String newPath = path.substring(0, path.lastIndexOf("/") + 1) + "user_photo1" + path.substring(path.lastIndexOf("."), path.length());
            copyFile(path, newPath);
            return compressBitmap;
        }
    }



    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int byteSum = 0;
            int byteRead ;
            File oldFile = new File(oldPath);
            if (oldFile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    byteSum += byteRead; // 字节数 文件大小
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 图像保存到文件中
     *
     * @param bmp
     * @param fileName
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static String saveBmpToFile(Bitmap bmp, String fileName) throws IOException {
        String path = null;
        if (fileName != null && fileName.contains("/")) {
            path = fileName;
        } else {
            path = CameraUtils.baseDir + "/" + fileName;
        }
        File basePath = new File(path.substring(0, path.lastIndexOf("/")));
        if (!basePath.exists()) {
            basePath.mkdirs();
        }
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }

        FileOutputStream outputStream ;
        outputStream = new FileOutputStream(path);
        if (fileName.endsWith(".webp") || fileName.endsWith(".WEBP")) {
            bmp.compress(Bitmap.CompressFormat.WEBP, 100, outputStream);
        } else if (fileName.endsWith(".png") || fileName.endsWith(".PNG")) {
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        } else {
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        }

        outputStream.close();
        return path;
    }


    /**
     * 等比例缩小图片
     * @param isTrans 是否按照480*800的比列压缩  还是原本图片的一半
     * @return
     */
    public static Bitmap MaxBitMap(Bitmap bitmap,boolean isTrans) {
        // TODO Auto-generated method stub
        //获取这个图片的宽和高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //定义预转换成的图片的宽度和高度
        int newWidth = 480;
        int newHeight = 800;
        if (!isTrans) {
            //根据最大的一边按照800 的比列缩放
            if (width>height) {
                newWidth= Max_SIZE;
                newHeight=(height* Max_SIZE)/width;
            }else {
                newHeight= Max_SIZE;
                newWidth=(width* Max_SIZE)/height;
            }
        }else {
            //如果按照480*800压缩  要先看看原本图片宽高那个长
            if (width>height) {
                newWidth=800;
                newHeight = 480;
            }
        }

        if (width>newWidth) {//比480大的缩放
            //计算缩放率，新尺寸除原始尺寸
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;

            // 创建操作图片用的matrix对象
            Matrix matrix = new Matrix();

            // 缩放图片动作
            matrix.postScale(scaleWidth, scaleHeight);
            // 创建新的图片
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    width, height, matrix, true);
            bitmap.recycle();
            return resizedBitmap;
        }else {
            return bitmap;
        }
    }

    /**
     * 获取文件大小
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static long getFileSizes(String path) throws Exception {
        long s = 0;
        File f = new File(path);
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s = fis.available();
        } else {
            f.createNewFile();
            System.out.println("文件不存在");
        }
        return s;
    }

    /**
     * 图片缩放
     *
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int width, int height) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;  //返回一个空的bitmap, 包含options.outHeight;与options.outWidth;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        if (width == 0 || height == 0) {
            options.inSampleSize = calculateInSampleSize(options, 480, 800);//获取缩放比例 用的是options.outHeight;与options.outWidth;
        } else {
            options.inSampleSize = calculateInSampleSize(options, width, height);
        }

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 缩放比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;

        if (reqWidth==-1) {
            int flag = width>height?width:height; //返回宽和高中较大的值,这个值和常量做比
            int inSampleSize = Math.round((float)flag / (float) Max_SIZE);//缩放比
            return inSampleSize;
        }else {
            int inSampleSize = 1;
            if (height > reqHeight || width > reqWidth) {
                final int heightRatio = Math.round((float) height
                        / (float) reqHeight);
                final int widthRatio = Math.round((float) width / (float) reqWidth);
                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }
            return inSampleSize;
        }
    }
}
