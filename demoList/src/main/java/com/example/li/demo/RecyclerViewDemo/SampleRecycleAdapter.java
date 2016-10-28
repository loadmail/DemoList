package com.example.li.demo.RecyclerViewDemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2016/6/1.
 */
public class SampleRecycleAdapter extends RecyclerView.Adapter<SampleRecycleAdapter.ViewHolder> {
    //保存列表项数据
    private final ArrayList<SampleModel> sampleData = DataModel.getData(30);//数据

    //创建列表项中显示的控件对象（需要使用Adapter指定泛型）
    @Override
    public SampleRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //获取列表项控件LinearLayer对象
        View rowView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2,parent,false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(SampleRecycleAdapter.ViewHolder holder, int position) {
        SampleModel rowData = sampleData.get(position);
        holder.textView1.setText(rowData.str);
        holder.textView2.setText(rowData.str+"---");
        holder.itemView.setTag(rowData);
    }

    @Override
    public int getItemCount() {
        return sampleData.size();
    }
    /**
     * ViewHolder用于存储列表项中显示的控件，（这里只有一个TextView做示例）
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView1;
        private TextView textView2;

        public ViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(android.R.id.text1);
            textView2 = (TextView) itemView.findViewById(android.R.id.text2);
        }
    }

    /**删除指定列表项的数据
     * @param position
     */
    public void removeData(int position){
        sampleData.remove(position);
        //通知recycleView某个列表项被删除了
        notifyItemRemoved(position);
    }

    /**在指定的位置添加一个新的列表项
     * @param position
     */
    public void addItem(int position){
        //使用随机数字
        sampleData.add(position,new SampleModel("新增列表项"+ new Random().nextInt(100)));
        notifyItemInserted(position);
    }

}
