package com.example.li.demo.GlideTest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.module.GlideModule;
import com.example.li.demo.R;

public class GlideActivity extends AppCompatActivity implements GlideModule {
    ImageView targetView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        String url = "http://www.qq745.com/uploads/allimg/141106/1-141106153Q5.png";
        targetView = (ImageView) findViewById(R.id.iv_target);
        Glide.with(this).
                load(R.drawable.gif).
                asGif().
//                load(url).
//                asBitmap(). //强制处理为bitmap
                placeholder(android.R.drawable.presence_audio_online).//加载中显示的图片
                error(android.R.drawable.stat_notify_error).//加载失败时显示的图片
             //   crossFade().//淡入显示,注意:如果设置了这个,则必须要去掉asBitmap
             //       override(80,80).//设置最终显示的图片像素为80*80,注意:这个是像素,而不是控件的宽高
                 //    centerCrop().//中心裁剪,缩放填充至整个ImageView
                     skipMemoryCache(true).//跳过内存缓存
                diskCacheStrategy(DiskCacheStrategy.RESULT).//保存最终图片
                thumbnail(0.5f).//10%的原图大小
                into(targetView);//显示到目标View中
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
//    public class GlideActivity extends AppCompatActivity implements View.OnClickListener {
//        private ImageView mTargetView;
//        private String url = "http://www.qq745.com/uploads/allimg/141106/1-141106153Q5.png";
//        private String gif = "http://image24.360doc.com/DownloadImg/2011/03/0513/9707070_3.gif";
//        private String thumbnailUrl = "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg";
//        private File jpgFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/droid4xshare/test.jpg");
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_glide);
//            mTargetView = (ImageView) findViewById(R.id.iv_target);
//            findViewById(R.id.btn_load_bimap).setOnClickListener(this);
//            findViewById(R.id.btn_del_bimap).setOnClickListener(this);
//            findViewById(R.id.btn_load_gif).setOnClickListener(this);
//            findViewById(R.id.btn_load_file).setOnClickListener(this);
//            findViewById(R.id.btn_load_resource).setOnClickListener(this);
//            findViewById(R.id.btn_load_animid).setOnClickListener(this);
//            findViewById(R.id.btn_load_anim).setOnClickListener(this);
//            findViewById(R.id.btn_load_thumbnailUrl).setOnClickListener(this);
//            findViewById(R.id.btn_load_thumbnailSize).setOnClickListener(this);
//
//            Glide.get(GlideActivity.this).clearMemory();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Glide.get(GlideActivity.this).clearDiskCache();
//                }
//            }).start();
//        }
//
//        @Override
//        public void onClick(View v) {
//            ImageLoadConfig.OverrideSize size = new ImageLoadConfig.OverrideSize(100, 100);
//            switch (v.getId()) {
//                case R.id.btn_del_bimap://删除已加载过url的图片
//                    ImageLoader.clearTarget(this, url);
//                    break;
//                case R.id.btn_load_bimap: //加载网络图片
//                    ImageLoader.loadStringRes(mTargetView, url, ImageLoader.defConfig, mListener);
//                    break;
//                case R.id.btn_load_gif://加载网络的gif
//                    ImageLoadConfig config2 = ImageLoadConfig.parseBuilder(ImageLoader.defConfig).setAsGif(true).
//                            setSkipMemoryCache(true).
//                            setDiskCacheStrategy(ImageLoadConfig.DiskCache.NONE).
//                            build();
//                    ImageLoader.loadGif(mTargetView, gif, config2, mListener);
//                    break;
//                case R.id.btn_load_file://加载本地图片
//                    ImageLoadConfig config3 = ImageLoadConfig.parseBuilder(ImageLoader.defConfig).
//                            setSkipMemoryCache(true).
//                            setDiskCacheStrategy(ImageLoadConfig.DiskCache.NONE).setSize(size).
//                            build();
//                    ImageLoader.loadFile(mTargetView, jpgFile, config3, mListener);
//                    break;
//                case R.id.btn_load_resource://加载本地资源
//                    ImageLoadConfig config4 = ImageLoadConfig.parseBuilder(ImageLoader.defConfig).
//                            setSkipMemoryCache(true).
//                            setDiskCacheStrategy(ImageLoadConfig.DiskCache.NONE)
//                            .build();
//                    ImageLoader.loadResId(mTargetView, R.drawable.dog, config4, mListener);
//                    break;
//                case R.id.btn_load_animid://加载资源动画
//                    ImageLoadConfig config5 = ImageLoadConfig.parseBuilder(ImageLoader.defConfig).
//                            setAnimResId(R.anim.left_in).
//                            setSkipMemoryCache(true).
//                            setDiskCacheStrategy(ImageLoadConfig.DiskCache.NONE).
//                            setAsGif(true).build();
//                    ImageLoader.loadResId(mTargetView, R.drawable.smail, config5, mListener);
//                    break;
//                case R.id.btn_load_anim://加载属性动画
//                    ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
//                        @Override
//                        public void animate(View view) {
//                            ObjectAnimator moveIn = ObjectAnimator.ofFloat(view, "translationX", -500f, 0f);
//                            ObjectAnimator rotate = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
//                            ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 1f);
//                            ObjectAnimator moveTop = ObjectAnimator.ofFloat(view, "translationY", 0f, -2000, 0f);
//                            AnimatorSet animSet = new AnimatorSet();
//                            animSet.play(rotate).with(fadeInOut).after(moveIn).before(moveTop);
//                            animSet.setDuration(5000);
//                            animSet.start();
//                        }
//                    };
//                    ImageLoadConfig config6 = ImageLoadConfig.parseBuilder(ImageLoader.defConfig).
//                            setAnimator(animationObject).
//                            setSkipMemoryCache(true).
//                            setDiskCacheStrategy(ImageLoadConfig.DiskCache.NONE).
//                            setAsGif(true).
//                            build();
//                    ImageLoader.loadResId(mTargetView, R.drawable.smail, config6, mListener);
//                    break;
//
//
//                case R.id.btn_load_thumbnailUrl://先加载缩略图片,再显示原图
//                    ImageLoadConfig config7 = ImageLoadConfig.parseBuilder(ImageLoader.defConfig).
//                            setSkipMemoryCache(true).
//                            setDiskCacheStrategy(ImageLoadConfig.DiskCache.NONE).
//                            setThumbnailUrl(thumbnailUrl)
//                            .build();
//                    ImageLoader.loadStringRes(mTargetView, url, config7, mListener);
//                    break;
//                case R.id.btn_load_thumbnailSize:
//                    ImageLoadConfig config8 = ImageLoadConfig.parseBuilder(ImageLoader.defConfig).
//                            setSkipMemoryCache(true).
//                            setDiskCacheStrategy(ImageLoadConfig.DiskCache.NONE).
//                            setThumbnail(0.7f)
//                            .build();
//                    ImageLoader.loadStringRes(mTargetView, url, config8, mListener);
//                    break;
//
//            }
//        }
//
//        private LoaderListener mListener = new LoaderListener() {
//
//            @Override
//            public void onSuccess() {
//                System.out.println("onSuccess()");
//            }
//
//            @Override
//            public void onError() {
//                System.out.println("onError()");
//            }
//        };
//
//    }

