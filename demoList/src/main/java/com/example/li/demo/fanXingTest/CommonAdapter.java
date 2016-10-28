package com.example.li.demo.fanXingTest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**万能适配器 :用法
 *
 * List<String> datas=new ArrayList<>();
 datas.add("万能适配器测试1");
 datas.add("万能适配器测试2");
 datas.add("万能适配器测试3");
 datas.add("万能适配器测试4");
 listView.setAdapter(new CommonAdapter<String>(context,datas,R.layout.item) {

@Override
protected void convertView(View item, String s) {
TextView textView=  CommonViewHolder.get(item,R.id.textView);
textView.setText(s);
}
});
 * Created by ly on 2016/10/20 11:39.
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    private Context context;
    private List<T> datas;
    private int layoutId;

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.context = context;
        this.datas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutId, null);
        }
        T t = getItem(position);
        convertView(convertView, t);
        return convertView;
    }

    /**
     * 需要去实现的对item中的view的设置操作
     * @param item
     * @param t
     */
    protected abstract void convertView(View item, T t);

}
