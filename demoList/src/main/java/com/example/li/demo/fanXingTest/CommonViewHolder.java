package com.example.li.demo.fanXingTest;

import android.util.SparseArray;
import android.view.View;

/**用法:
 * @Override
public View getView(int position, View convertView, ViewGroup parent) {

if (convertView == null) {
convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
}

TextView name= CommonViewHolder.get(convertView, R.id.name);

name.setText("sss");

return convertView;
}

 */

public class CommonViewHolder {

    /**
     * @param view 所有缓存View的根View
     * @param id   缓存View的唯一标识
     * @return
     */
    public static <T extends View> T get(View view, int id) {
        // TODO: 2016/10/20 一直不太懂view.getTag()的意思
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        //如果根view没有用来缓存View的集合
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);//创建集合和根View关联
        }
        View childView = viewHolder.get(id);//获取根View储存在集合中的孩纸
        if (childView == null) {//如果没有改孩纸
            //找到该孩纸
            childView = view.findViewById(id);
            viewHolder.put(id, childView);//保存到集合
        }
        return (T) childView;
    }
}
