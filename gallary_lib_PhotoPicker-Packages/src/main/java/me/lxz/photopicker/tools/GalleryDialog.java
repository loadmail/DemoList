package me.lxz.photopicker.tools;

/**
 * Created by Lin1 on 15/7/27.
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

import me.lxz.photopicker.R;
import me.lxz.photopicker.adapter.AbstractViewPagerAdapter;
import me.relex.circleindicator.CircleIndicator;


public class GalleryDialog {

    private final LayoutInflater inflater;
    private Context context;
    private boolean isShowDel = false;
    private OnDeleteFileListen onDeleteFileisten;


    public GalleryDialog(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    private View root;

    public void startFile(List<File> list, int index, boolean isShowDel) {
        this.isShowDel = isShowDel;
        startFile(list, index);
    }

    public void startFile(List<File> list, int index) {

        guideDialog = new Dialog(context, R.style.dialog_fullscreen);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_photo_show, null);
        guideDialog.setContentView(view);
        // CUSTOM
        ViewPager customViewpager = (ViewPager) view.findViewById(R.id.viewpager_custom);
        CircleIndicator customIndicator = (CircleIndicator) view.findViewById(R.id.indicator_custom);
        GalleryAdapter customPagerAdapter = new GalleryAdapter(list);
        customViewpager.setAdapter(customPagerAdapter);
        customViewpager.setCurrentItem(index);
        customIndicator.setViewPager(customViewpager);
        customViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.d("OnPageChangeListener", "Current selected = " + i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        guideDialog.show();
//        http://blog.csdn.net/maosidiaoxian/article/details/38661589

    }

    public class GalleryAdapter extends AbstractViewPagerAdapter {


        public GalleryAdapter(List data) {
            super(data);
        }

        @Override
        public View newView(int position) {
            View view = inflater.inflate(R.layout.viewpage_photo, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            File file = (File) (mData.get(position));
            String url = "file://" + file.getAbsolutePath();
            SimpleImageLoader.displayImage(url, imageView, ImageView.ScaleType.FIT_CENTER);

            View del = view.findViewById(R.id.del);
            if (isShowDel) {
                del.setVisibility(View.VISIBLE);
                del.setTag(position);
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) (Integer) v.getTag();
                        File delFile = (File) mData.get(position);
                        mData.remove(delFile);
                        guideDialog.dismiss();
                        if (onDeleteFileisten != null)
                            onDeleteFileisten.onDeleteFile(mData, delFile, position);
                    }
                });


            } else {
                del.setVisibility(View.GONE);
            }
            return view;
        }

    }

    Dialog guideDialog;

    public void startUrl(List<String> list, int index) {

        guideDialog = new Dialog(context, R.style.dialog_fullscreen);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_photo_show, null);
        guideDialog.setContentView(view);
        // CUSTOM
        ViewPager customViewpager = (ViewPager) view.findViewById(R.id.viewpager_custom);
        CircleIndicator customIndicator = (CircleIndicator) view.findViewById(R.id.indicator_custom);
        GalleryUrlAdapter customPagerAdapter = new GalleryUrlAdapter(list);
        customViewpager.setAdapter(customPagerAdapter);
        customViewpager.setCurrentItem(index);
        customIndicator.setViewPager(customViewpager);
        customViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.d("OnPageChangeListener", "Current selected = " + i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        guideDialog.show();

    }

    public class GalleryUrlAdapter extends AbstractViewPagerAdapter {


        public GalleryUrlAdapter(List data) {
            super(data);
        }

        @Override
        public View newView(int position) {
            ImageView view = new ImageView(context);
            view.setBackgroundColor(Color.BLACK);
            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            String url = mData.get(position).toString();
            SimpleImageLoader.displayImage(url, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        guideDialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return view;
        }
    }

    public interface OnDeleteFileListen {
        void onDeleteFile(List<File> files, File file, int position);

    }

    public OnDeleteFileListen getOnDeleteFileisten() {
        return onDeleteFileisten;
    }

    public GalleryDialog setOnDeleteFileisten(OnDeleteFileListen onDeleteFileisten) {
        this.onDeleteFileisten = onDeleteFileisten;
        return this;
    }
}
