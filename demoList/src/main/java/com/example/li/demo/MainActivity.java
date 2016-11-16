package com.example.li.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.li.demo.CatchExceptionTest.CatchExceptionLogActivity;
import com.example.li.demo.CheckBoxTest.SingleCheckActivity;
import com.example.li.demo.ColorBall.ColorBallActivity;
import com.example.li.demo.GlideTest.GlideActivity;
import com.example.li.demo.MultiLevel.MultiLevelActivity;
import com.example.li.demo.RecyclerViewDemo.CardLayoutManager.CardLayoutActivity;
import com.example.li.demo.RecyclerViewDemo.RecyclerViewActivity;
import com.example.li.demo.tab.TabMainActivity;
import com.example.li.demo.testComAdapter.ComAdapterAty;
import com.example.li.demo.testThreadpool.ThreadPoolActivity;
import com.example.li.demo.testTouch.TouchActivity;
import com.example.li.demo.view.ListViewForScroll;
import com.example.li.demo.view.threeDView.ThreeDActivity;
import com.example.li.demo.view.threeDView.WeatherActivity;
import com.example.li.demo.view.toutiao.ScrollTopActivity;
import com.example.li.demo.view.xiuyixiu.RadarActivity;

public class MainActivity extends AppCompatActivity {
    ListViewForScroll listView;
    private static final DemoInfo[] DEMOS = {
            new DemoInfo("ThreadPoolActivity", "ThreadPoolActivity", ThreadPoolActivity.class),
            new DemoInfo("TouchActivity", "TouchActivity", TouchActivity.class),
            new DemoInfo("RecyclerViewActivity", "RecyclerViewActivity", RecyclerViewActivity.class),
            new DemoInfo("GlideActivity", "GlideActivity", GlideActivity.class),
            new DemoInfo("SingleCheckActivity", "SingleCheckActivity", SingleCheckActivity.class),
            new DemoInfo("ComAdapterAty", "ComAdapterAty", ComAdapterAty.class),
            new DemoInfo("CatchExceptionLogActivity", "CatchExceptionLogActivity", CatchExceptionLogActivity.class),
            new DemoInfo("RadarActivity", "咻一咻", RadarActivity.class),
            new DemoInfo("ThreeDActivity", "3d效果,3D罗盘", ThreeDActivity.class),
            new DemoInfo("WeatherActivity", "3d天气效果", WeatherActivity.class),
            new DemoInfo("CardLayoutActivity", "菱形", CardLayoutActivity.class),
            new DemoInfo("ScrollTopActivity", "滚动头条", ScrollTopActivity.class),
            new DemoInfo("TabMainActivity", "tabView", TabMainActivity.class),
            new DemoInfo("MultiLevelActivity", "多层级的列表", MultiLevelActivity.class),
            new DemoInfo("ColorBallActivity", "选择双色球", ColorBallActivity.class),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListViewForScroll) findViewById(R.id.list);
        listView.setAdapter(new DemoListAdapter());
        listView.setOnItemClickListener((parent, view, position, id) -> onListItemClick(position));
    }

    private void onListItemClick(int position) {
        startActivity(new Intent(MainActivity.this, DEMOS[position].aClass));
    }

    public class DemoListAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(MainActivity.this, R.layout.demo_info_item, null);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView desc = (TextView) convertView.findViewById(R.id.desc);
            title.setText(DEMOS[position].name);
            desc.setText(DEMOS[position].desc);
            return convertView;
        }
        @Override
        public int getCount() {
            return DEMOS.length;
        }

        @Override
        public Object getItem(int position) {
            return DEMOS[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }


    private static class DemoInfo {
        private final String name;
        private final String desc;
        private final Class<? extends Activity> aClass;

        public DemoInfo(String name, String desc, Class<? extends Activity> aClass) {
            this.name = name;
            this.desc = desc;
            this.aClass = aClass;
        }
    }

}
