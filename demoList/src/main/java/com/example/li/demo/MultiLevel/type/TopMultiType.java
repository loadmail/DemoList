package com.example.li.demo.MultiLevel.type;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.li.demo.R;

/**
 * Created by ly on 2016/11/1 13:57.
 */

public class TopMultiType implements MultiType {
    private int k;

    public TopMultiType(int k) {
        this.k = k;
    }

    @Override
    public int getLayout() {
        return R.layout.item_order_top;
    }

    @Override
    public boolean isCheckable() {
        return false;
    }

    @Override
    public View getView(Context mContext, View convertView) {
        convertView = View.inflate(mContext,getLayout(),null);
     ((TextView) convertView.findViewById(R.id.top)).setText("top--"+k);
        return convertView;
    }
}
