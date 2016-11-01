package com.example.li.demo.MultiLevel.type;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.li.demo.R;

/**
 * Created by ly on 2016/11/1 15:55.
 */

public class BottomMultiType implements MultiType {
    private int k;
    public BottomMultiType(int k) {
        this.k = k;
    }

    @Override
    public int getLayout() {
        return R.layout.item_order_bottom;
    }

    @Override
    public boolean isCheckable() {
        return false;
    }

    @Override
    public View getView(Context mContext, View convertView) {
        convertView = View.inflate(mContext,getLayout(),null);
        ((TextView)convertView. findViewById(R.id.bottom)).setText("bottom--"+k);
        return convertView;
    }
}
