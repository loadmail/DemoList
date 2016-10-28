package com.example.uploadimage.cameraUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Locale;

/**
 * 照片选择工具类
 * 权限
 *
 *   <!-- 在SDCard中创建与删除文件权限 -->
 <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
 <!-- 往SDCard写入数据权限 -->
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 <uses-feature android:name="android.hardware.camera" />
 <uses-feature android:name="android.hardware.camera.autofocus" />
 */
public class CameraUtils {
    public static final String basePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar;
    public static final String baseDir =basePath +"img_cache";
    public static String imgPath;

    public static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_CHOOSE = 2;
    // String  basePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar;
//basePath: /storage/sdcard0/

    /**
     * 检查SD卡是否挂载
     *
     * @return
     */
    public static boolean checkSDcard() {
       boolean flag = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!flag) { // 检测sd是否可用
          Log.e("执行", "sd卡不可用: ");
        }
        return flag;
    }

    /**
     * 选择图片路径弹窗
     *
     * @param ct
     */
    public static void selectImage(final Context ct) {

        String state = Environment.getExternalStorageState();
        File dir;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            dir = new File(baseDir);
        } else {
            dir = ct.getFilesDir();
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String imgName = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        imgPath = new File(dir, imgName).getAbsolutePath();
        String[] selectPicTypeStr = {"拍照", "相册"};
        new AlertDialog.Builder(ct).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        })
                .setItems(selectPicTypeStr,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    // 相机拍摄
                                    case 0:
                                        openCamera(ct);
                                        break;
                                    // 手机相册
                                    case 1:
                                        chosePicture(ct);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }).show();
    }

    /**
     * 打开照相机
     */
    public static void openCamera(Context context) {
        // TODO: 2016/5/25 可能是拍照后裁剪 
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri cameraUri ;
        if (checkSDcard()) {
            cameraUri = Uri.fromFile(new File(CameraUtils.imgPath));
        } else {
            cameraUri = InternalStorageContentProvider.CONTENT_URI;
        }
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, cameraUri);
        intent.putExtra("return-data", true);
        ((Activity) context).startActivityForResult(intent, REQUEST_CAMERA);
    }

    /**
     * 本地相册选择图片
     */
    public static void chosePicture(Context context) {

        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // TODO: 2016/5/25 可以在这里添加crop
        //根据版本号不同使用不同的Action
        //在android4.4的系统上，没选图片直接返回的会 会直接退出程序，在4.2的系统上 会报错。
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }
        ((Activity) context).startActivityForResult(intent, REQUEST_CHOOSE);
    }

    /**
     * bitmap转base64
     *
     * @return
     */
    public static String imgToBase64(Bitmap bitmap) {
        if (bitmap == null) {
            //bitmap not found!!
            return "";
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();  //注意权限
            out.close();

            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 被Activity中的onActivityResult回调
     *
     * @param ct       Activity
     * @param callBack 实现类
     */
    public static void onActivityResult(Context ct, int requestCode, int resultCode, Intent data, DataCallBack callBack) {
        Bitmap bitmap = null;
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        uri2File(ct,data,CameraUtils.imgPath);//重新保存图片
        try {
          bitmap =  CompressUtil.compressImage(CameraUtils.imgPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        callBack.setData(bitmap);
    }

    /**
     * 传入intent,获取uri转化为输入流,在固定路径创建新的图片文件
     * @param data
     */
    public static void uri2File(Context ct,Intent data,String path) {
        if (data != null) {
            Uri mUri = data.getData();

            if (mUri != null) {
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;
                File file;
                try {
                    inputStream = ct.getContentResolver().openInputStream(mUri);
                    file = new File(path);
                    fileOutputStream = new FileOutputStream(file);
                    copyStream(inputStream, fileOutputStream);//保存新图片

                    //这四步不能写到方法里,因为
                    fileOutputStream.close();
                    inputStream.close();

                    fileOutputStream = null;
                    inputStream = null;
                    // TODO: 2016/5/26 跳转到裁剪

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("执行", "图片保存失败--:");
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Exception e2) {

                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Exception e2) {

                        }
                    }
                }
            } else {

            }
        }
    }

    /**
     * 从图库选择后将图片发送到裁剪界面
     *
     * @param input
     * @param output
     * @throws IOException
     */
    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    /**
     * 处理bitmap的接口
     */
    public interface DataCallBack<T> {
        void setData(T t);
    }


    /**
     * 从相机中获取bitmap
     *
     * @param data
     * @return
     */
    public static Bitmap getBitmapFromCamera(Intent data) {
        String realPath = data.getData().getPath();
        Bitmap bitmap;
        Bundle bundle = data.getExtras();
        bitmap = (Bitmap) bundle.get("data");//得到bitmap对象
        return bitmap;
    }

    /**
     * 有些机型取不到
     * 从相册通过真实路径获取bitmap
     *
     * @param ct
     * @param data
     * @return
     */
    public static Bitmap getBitmapFromGallery(Context ct, Intent data) {
        Bitmap bitmap;
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = ct.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);  //查询
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]); //这里每一步获取的是什么东西还不太清楚
        String realPath = cursor.getString(columnIndex);
        cursor.close();
        bitmap = BitmapFactory.decodeFile(realPath);
        return bitmap;
    }
}
