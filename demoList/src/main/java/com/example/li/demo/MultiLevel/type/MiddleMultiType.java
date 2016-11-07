package com.example.li.demo.MultiLevel.type;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.li.demo.MultiLevel.Goods;
import com.example.li.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ly on 2016/11/1 13:57.
 */

public class MiddleMultiType implements MultiType {

    private List<Goods> list;
    private Goods orderGoods;

    public MiddleMultiType(Goods orderGoods) {
        this.orderGoods = orderGoods;
        list = new ArrayList<>();
        list.add(orderGoods);
    }
    @Override
    public int getLayout() {
        return R.layout.item_order_in;
    }

    @Override
    public boolean isCheckable() {
        return false;
    }

    @Override
    public View getView(Context mContext, View convertView) {
        convertView = View.inflate(mContext,getLayout(),null);
        TextView nameTv = (TextView) convertView.findViewById(R.id.good_name);
        nameTv.setText(orderGoods.getGoodName());
        nameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, orderGoods.getGoodName(), Toast.LENGTH_SHORT).show();
            }
        });
        TextView snTv = (TextView) convertView.findViewById(R.id.good_sn);
        snTv.setText(orderGoods.getGoodSn());
        snTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, orderGoods.getGoodSn(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
