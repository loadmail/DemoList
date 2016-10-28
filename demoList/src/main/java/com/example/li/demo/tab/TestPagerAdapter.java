package com.example.li.demo.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ly on 2016/10/28 11:48.
 */

public class TestPagerAdapter extends FragmentPagerAdapter{


    public TestPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }


    @Override
    public int getCount() {
        return 0;
    }
}
