package com.cn.demo.takephoto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import java.io.File;
import java.util.List;

import me.lxz.photopicker.camera.PhotoPickManger;
import me.lxz.photopicker.tools.PhotoGridManager;
import me.lxz.photopicker.tools.QQPhotoGridManager;
import me.lxz.photopicker.tools.SimpleImageLoader;
import me.lxz.photopicker.view.NoScrollGridview;

/**
 * Created by Lin on 16/5/17.
 */
public class AsQQPhotoGridActivity extends Activity {

    private NoScrollGridview gridview1;
    private PhotoPickManger pickManger;
    private QQPhotoGridManager photoGridManager;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pickManger.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        pickManger.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && photoGridManager.isShakeAnim() || photoGridManager.isShakeAnim()) {
            photoGridManager.setShakeAnim(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        gridview1 = (NoScrollGridview) findViewById(R.id.gridview1);
        findViewById(R.id.gridview2).setVisibility(View.GONE);
        SimpleImageLoader.init(this);

        {
            pickManger = new PhotoPickManger("pick", this, savedInstanceState, new PhotoPickManger.OnPhotoPickFinsh() {
                @Override
                public void onPhotoPick(List<File> list) {
                  photoGridManager.getAdapter().notifyDataSetChanged();
                }
            });
            photoGridManager = new QQPhotoGridManager(gridview1, pickManger, 8, 4);
            photoGridManager.setDrawableAdd(me.lxz.photopicker.R.drawable.addphoto);
            photoGridManager.setDrawableDel(me.lxz.photopicker.R.drawable.photo_del_black);


            photoGridManager.setOnItemAddAction(new PhotoGridManager.OnItemAction() {
                @Override
                public void onItemAciton(PhotoGridManager photoGridManager) {
                    pickManger.setReturnFileCount(8 - pickManger.getSelectsPhotos().size());
                    new AlertDialog.Builder(pickManger.getActivity()).setTitle("单选框").setIcon(
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

            pickManger.flushBundle();
        }

    }
}
