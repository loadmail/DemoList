package com.example.li.demo.MultiLevel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.li.demo.MultiLevel.type.MultiType;

import java.util.List;

/**
 * Created by ly on 2016/11/1 12:01.
 */

public class MultiLevelAdapter<T extends MultiType> extends RecyclerView.Adapter {
    private List<T> list;
    private Context mContext;

    public MultiLevelAdapter(Context mContext,List<T> list) {
        this.list = list;
        this.mContext = mContext;
    }


    @Override
    public MultiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // TODO: 2016/11/1 这里可以根据不同的viewType加载不同的布局
        MultiViewHolder holder = new MultiViewHolder(
                list.get(viewType).
                        getView
                        (mContext,parent));
        return holder;
    }
    /** todo  关键代码  viewType就是item position
     *
     * 每一个位置的item都作为单独一项来设置
     * viewType 设置为position
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return  position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MultiViewHolder extends RecyclerView.ViewHolder {
        public MultiViewHolder(View itemView) {
            super(itemView);
        }
    }

}
