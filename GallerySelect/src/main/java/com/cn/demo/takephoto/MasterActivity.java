package com.cn.demo.takephoto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import java.io.File;
import java.util.List;

import me.lxz.photopicker.camera.PhotoPickManger;
import me.lxz.photopicker.tools.PhotoGridManager;
import me.lxz.photopicker.tools.SimpleImageLoader;
import me.lxz.photopicker.view.NoScrollGridview;

/**
 * Created by Lin on 16/5/17.
 */
public class MasterActivity extends Activity {

    private NoScrollGridview gridview1;
    private PhotoPickManger pickManger1;
    private PhotoGridManager photoGridManager1;

    private PhotoPickManger pickManger2;
    private NoScrollGridview gridview2;
    private PhotoGridManager photoGridManager2;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pickManger1.onActivityResult(requestCode, resultCode, data);
        pickManger2.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        pickManger1.onSaveInstanceState(savedInstanceState);
        pickManger2.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        gridview1 = (NoScrollGridview) findViewById(R.id.gridview1);
        gridview2 = (NoScrollGridview) findViewById(R.id.gridview2);
        SimpleImageLoader.init(this.getApplicationContext());

        {
            pickManger1 = new PhotoPickManger("pick1", this, savedInstanceState, new PhotoPickManger.OnPhotoPickFinsh() {
                @Override
                public void onPhotoPick(List<File> list) {
                    photoGridManager1.getAdapter().notifyDataSetChanged();
                }
            });
            photoGridManager1 = new PhotoGridManager(gridview1, pickManger1, 8, 4);
            ;
            setPickMangerAndPhotoGridManger(pickManger1, photoGridManager1);
        }

        {
            pickManger2 = new PhotoPickManger("pick2", this, savedInstanceState, new PhotoPickManger.OnPhotoPickFinsh() {
                @Override
                public void onPhotoPick(List<File> list) {
                    photoGridManager2.getAdapter().notifyDataSetChanged();
                }
            });
            photoGridManager2 = new PhotoGridManager(gridview2, pickManger2, 8, 4);
            ;
            setPickMangerAndPhotoGridManger(pickManger2, photoGridManager2);
        }
    }

    public void setPickMangerAndPhotoGridManger(final PhotoPickManger pickManger,PhotoGridManager photoGridManager){
        photoGridManager.setDrawableAdd(me.lxz.photopicker.R.drawable.addphoto);
        photoGridManager.setDrawableDel(me.lxz.photopicker.R.drawable.photo_del_black);
        final PhotoGridManager finalPhotoGridManager = photoGridManager;
        pickManger.setOnPhotoPickFinsh(new PhotoPickManger.OnPhotoPickFinsh() {
            @Override
            public void onPhotoPick(List<File> list) {
                finalPhotoGridManager.getAdapter().notifyDataSetChanged();
            }
        });

        photoGridManager.setOnItemAddAction(new PhotoGridManager.OnItemAction() {
            @Override
            public void onItemAciton(PhotoGridManager photoGridManager) {
                pickManger.setReturnFileCount(8 - pickManger.getSelectsPhotos().size());
                createDialog(pickManger);
            }
        });
        pickManger.flushBundle();
    }

    public void createDialog(final PhotoPickManger pickManger){
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK&&photoGridManager1.isShakeAnim()||photoGridManager2.isShakeAnim()) {
//            photoGridManager1.setShakeAnim(false);
//            photoGridManager2.setShakeAnim(false);
//            return true;
//        }

        return super.onKeyDown(keyCode, event);
    }
}
