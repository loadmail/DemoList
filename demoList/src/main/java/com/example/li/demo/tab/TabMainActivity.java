package com.example.li.demo.tab;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.li.demo.R;


public class TabMainActivity extends FragmentActivity {
    private IndicatorViewPager indicatorViewPager;
    private View centerView;
    private FixedIndicatorView indicator;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_tab_main);
        // TODO: 2016/10/27 不可滑动的viewpager 只是重写了分发和消费事件,返回false
        ViewPager viewPager = (ViewPager) findViewById(R.id.tabmain_viewPager);
        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(4);  // TODO: 2016/10/28 viewpager里面还有指示器 是fragment里的


        indicator = (FixedIndicatorView) findViewById(R.id.tabmain_indicator); // Todo 导航的指示器  主页 消息 发现...
        // TODO: 2016/10/27 滑动中过渡变化的颜色 包含了字体的变化 按照百分率变化  OnTransitionTextListener.onTransition()
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(Color.RED, Color.GRAY));

        //这里可以添加一个view，作为centerView，会位于Indicator的tab的中间
        centerView = getLayoutInflater().inflate(R.layout.tab_main_center, indicator, false);
        indicator.setCenterView(centerView);  // TODO: 2016/10/28 将view添加到中心  这个方法可以看一下


        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        // TODO: 2016/10/28 这个adapter目的就是添加fragment 这个adapter直接把数据封装好了
        indicatorViewPager.setAdapter(new NavigationAdapter(getSupportFragmentManager()));// TODO: 2016/10/26 主页 消息...

        centerView.setOnClickListener(v -> {
            if (v == centerView) {
                //todo 还可以移除哦
                indicator.removeCenterView(); //移除中心view
            }
        });
    }


    private class NavigationAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        private LayoutInflater inflater;
        private String[] tabNames = {"主页", "消息", "发现", "我"};
        int selector_src = R.drawable.maintab_selector;
        private int[] tabIcons = {selector_src, selector_src, selector_src, selector_src};

        public NavigationAdapter(FragmentManager fm) {
             super(fm);  // TODO: 2016/10/28 构造必须传入的
            inflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.tab_main, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(tabNames[position]);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[position], 0, 0);
            return textView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            FirstLayerFragment mainFragment = new FirstLayerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(FirstLayerFragment.INTENT_STRING_TABNAME, tabNames[position]);
            bundle.putInt(FirstLayerFragment.INTENT_INT_INDEX, position);
            mainFragment.setArguments(bundle);
            return mainFragment;
        }
    }
}

