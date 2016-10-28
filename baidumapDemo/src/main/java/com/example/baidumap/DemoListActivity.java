package com.example.baidumap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.model.LatLng;

/**
 * listActivity 内部类DemoInfo
 */
public class DemoListActivity extends Activity {
    private ListView mListView;
    private final static DemoInfo[] DEMOS = {
            new DemoInfo("第一个百度定位", "MainActivity", MainActivity.class),
            new DemoInfo("第二个百度定位", "MapActivity", MapActivity.class),
            new DemoInfo("广告sdk", "AdActivity", AdActivity.class)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_list);
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(new DemoListAdapter());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                onListItemClick(index);
            }
        });
    }

    /**
     * item onclick
     *
     * @param index
     */
    private void onListItemClick(int index) {
        Intent intent = null;
        intent = new Intent(DemoListActivity.this, DEMOS[index].demoClass);
        this.startActivity(intent);
    }


    /**
     * adapter
     */
    public class DemoListAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(DemoListActivity.this, R.layout.demo_info_item, null);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView desc = (TextView) convertView.findViewById(R.id.desc);
            title.setText(DEMOS[position].title);
            desc.setText(DEMOS[position].desc);
            if (position >= 16) {
                title.setTextColor(Color.YELLOW);
            }
            return convertView;
        }

        @Override
        public int getCount() {
            return DEMOS.length;
        }

        @Override
        public Object getItem(int index) {
            return DEMOS[index];
        }

        @Override
        public long getItemId(int id) {
            return id;
        }
    }

    /**
     * 内部类
     */
    private static class DemoInfo {
        private final String title;  //也可以是资源id
        private final String desc;
        private final Class<? extends Activity> demoClass;

        public DemoInfo(String title, String desc, Class<? extends Activity> demoClass) {
            this.title = title;
            this.desc = desc;
            this.demoClass = demoClass;
        }
    }

    /**
     * --state--:双击,当前经度： 116.368131 当前纬度：39.788070
     * zoom=13.4 rotate=316 overlook=0
     *
     * @param touchType
     * @param currentPt
     * @param mBaiduMap
     */

    public String stateData(String touchType, LatLng currentPt, BaiduMap mBaiduMap) {
        String state = String.format(touchType + ",当前经度： %f 当前纬度：%f", currentPt.longitude, currentPt.latitude);
        state += "\n";
        MapStatus ms = mBaiduMap.getMapStatus();
        state += String.format("zoom=%.1f rotate=%d overlook=%d", ms.zoom, (int) ms.rotate, (int) ms.overlook);
        return state;
    }
}
