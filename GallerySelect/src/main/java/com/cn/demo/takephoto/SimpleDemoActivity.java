package com.cn.demo.takephoto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import me.lxz.photopicker.camera.PhotoPickManger;
import me.lxz.photopicker.tools.SimpleImageLoader;

public class SimpleDemoActivity extends AppCompatActivity {

    PhotoPickManger pickManger;
    private View btn;
    private ImageView img;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**图片加载器*/
        SimpleImageLoader.init(this.getApplicationContext());


        btn = findViewById(R.id.btn);
        img = (ImageView) findViewById(R.id.img);
        tv=(TextView)findViewById(R.id.tv);

        pickManger = new PhotoPickManger("pick",this, savedInstanceState,new PhotoPickManger.OnPhotoPickFinsh() {
            @Override
            public void onPhotoPick(List<File> list) {
                tv.setText("");
                Toast.makeText(getApplicationContext(), "path:" + list.get(0).getPath() + " length:" + list.get(0).length(), Toast.LENGTH_SHORT).show();
                tv.append("path:" + list.get(0).getPath());
                tv.append("\nlength:" + new DecimalFormat("#.##").format((1.0d*list.get(0).length()/1024/1024))+"MB");
                /**是否图片压缩*/
                processImg();

            }
        });
        /**是否在*/
        pickManger.setCut(false);
        pickManger.flushBundle();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickManger.clearCache();
                pickManger.start(PhotoPickManger.Mode.SYSTEM_CAMERA);
            }
        });


    }
    /**图片压缩*/
    private void processImg() {
        pickManger.doProcessedPhotos(new PhotoPickManger.OnProcessedPhotos() {
            @Override
            public void onProcessed(List<File> list) {
                SimpleImageLoader.displayImage(list.get(0), img);
                tv.append("\nprogress length:" + new DecimalFormat("#.##").format((1.0d*list.get(0).length()/1024/1024))+"MB");
           }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pickManger.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        pickManger.onSaveInstanceState(savedInstanceState);
    }
}