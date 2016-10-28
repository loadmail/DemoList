package com.example.li.demo.testComAdapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.example.li.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过匿名内部类获得适配器，适配内的convert方法只需一行代码即可为对应的view赋值
 */
public class ComAdapterAty extends Activity {
    private List<ArticleBean> beanList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.layout_comadapter);
        ListView listView = (ListView) findViewById(R.id.list);

        beanList = new ArrayList<ArticleBean>();
        for(int i=1;i<10;i++){
            ArticleBean bean = new ArticleBean("文章"+i,"内容"+i+":周三早上丢失了红色钱包，在食堂二楼","2016060"+i,"1718013691"+i);
            beanList.add(bean);
        }

        //直接使用匿名内部类即可取得适配器
        ComAdapter mAdapter = new ComAdapter<ArticleBean>(context, beanList, R.layout.item_string_listview) {
            @Override
            public void convert(ViewHolder holder, ArticleBean item) {//通过ViewHolder找到view，通过Item设置值
                holder.setTextView(R.id.tv_title, item.getTitle());
                holder.setTextView(R.id.tv_describe, item.getContent());
                holder.setTextView(R.id.tv_time, item.getTime());
                holder.setTextView(R.id.tv_phone, item.getPhone());
            }
        };
        listView.setAdapter(mAdapter);
    }
}
