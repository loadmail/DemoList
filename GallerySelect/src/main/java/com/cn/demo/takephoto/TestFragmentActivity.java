package com.cn.demo.takephoto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.List;

import me.lxz.photopicker.tools.SimpleImageLoader;

/**
 * Created by Lin on 16/5/15.
 */
public class TestFragmentActivity extends FragmentActivity {
    public String TAG = "test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        SimpleImageLoader.init(this.getApplicationContext());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new TestFragment()).commit();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**将事件传递给Fragment*/
        FragmentManager fm = getSupportFragmentManager();
        handlerFragmentOnActivityResult(fm, requestCode, resultCode, data);
    }

    public void handlerFragmentOnActivityResult(FragmentManager fragmentManager, int requestCode, int resultCode, Intent data) {
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> fragments = fm.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
                FragmentManager childFragmentManager = fragment.getChildFragmentManager();
                List<Fragment> childFragments = childFragmentManager.getFragments();
                if (childFragmentManager != null) {

                }
            }
        }
    }


}
