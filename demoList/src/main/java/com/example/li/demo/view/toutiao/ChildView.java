package com.example.li.demo.view.toutiao;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by ly on 2016/10/20 09:45.
 */

public class ChildView extends ScrollTopView<String> {
    public ChildView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void setNext(ScrollTopView<String>.ViewHolder vh, String s) {
        vh.nameTv.setText(s);
        vh.nameTv.setOnClickListener(v -> click.onAdapterClick(v,s));
    }
}
