package com.example.li.demo.testComAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.li.demo.R;

import java.util.List;

/**
 * Created by jiatao on 2016/6/10.
 * 这是最普通的适配器：
 *      MyAdapter继承BaseAdapter，然后getView里面使用ViewHolder模式；
 *      一般情况下，我们的写法是这样的：对于不同布局的ListView，我们会有一个对应的Adapter，在Adapter中又会有一个ViewHolder类来提高效率。
 *      这样出现ListView就会出现与之对应的Adapter类、ViewHolder类；
 *  那么有没有办法减少我们的编码呢？第二部分中将初步实现
 */
public class MyAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<String> mData;

    public MyAdapter(Context mContext, List<String> mData) {
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_string_listview, parent, false);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_title.setText(mData.get(position));
        return convertView;
    }

    class ViewHolder {
        private TextView tv_title;
    }
}
