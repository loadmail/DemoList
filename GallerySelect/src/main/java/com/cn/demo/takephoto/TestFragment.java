package com.cn.demo.takephoto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import me.lxz.photopicker.camera.PhotoPickManger;
import me.lxz.photopicker.tools.SimpleImageLoader;

/**
 * Created by Lin on 16/5/14.
 */
public class TestFragment extends Fragment {


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pickManger.onActivityResult(requestCode,resultCode,data);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
       pickManger.onSaveInstanceState(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_main,null);
        btn = view.findViewById(R.id.btn);
        img = (ImageView) view.findViewById(R.id.img);
        SimpleImageLoader.init(this.getContext().getApplicationContext());
        pickManger = new PhotoPickManger("pick",getActivity(), savedInstanceState,new PhotoPickManger.OnPhotoPickFinsh() {
            @Override
            public void onPhotoPick(List<File> list) {
                Toast.makeText(getActivity(), "" + list.get(0).getPath() + " " + list.get(0).length(), Toast.LENGTH_SHORT).show();
                SimpleImageLoader.displayImage(list.get(0), img);
            }
        });
        pickManger.flushBundle();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickManger.clearCache();
                pickManger.setCut(true);
                new AlertDialog.Builder(v.getContext()).setTitle("单选框").setIcon(
                        android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                        new String[]{"系统相机", "系统相册", "微信相册"}, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                switch (which) {
                                    case 0:
                                        pickManger.start(PhotoPickManger.Mode.SYSTEM_CAMERA);
                                        break;
                                    case 1:
                                        pickManger.start(PhotoPickManger.Mode.SYSTEM_IMGCAPTRUE);
                                        break;
                                    case 2:
                                        pickManger.start(PhotoPickManger.Mode.AS_WEIXIN_IMGCAPTRUE);
                                        break;
                                }
                            }
                        }).show();
            }
        });
        return view;
    }

    PhotoPickManger pickManger;
    private View btn;
    private ImageView img;




}
