package com.cn.demo.takephoto;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lin on 16/5/18.
 */
public class MainListActivity extends ListActivity{
    List<String> list=new ArrayList<String>();
    {
        list.add("简单的调用和图片压缩");
        list.add("在fragment中调用");
        list.add("多个拍照区分");
        list.add("仿QQ说说长按删除图片");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter adapter = new ArrayAdapter(
                     this, android.R.layout.simple_expandable_list_item_1, list);
         this.setListAdapter(adapter);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position)
        {
            case 0:
                startActivity(new Intent(v.getContext(),SimpleDemoActivity.class));
                break;
            case 1:
                startActivity(new Intent(v.getContext(),TestFragmentActivity.class));
                break;
            case 2:
                startActivity(new Intent(v.getContext(),MasterActivity.class));
                break;
            case 3:
                startActivity(new Intent(v.getContext(),AsQQPhotoGridActivity.class));
                break;
        }
    }
}
