package com.example.li.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by ly on 2016/10/17 09:32.
 */

public class ListViewForScroll extends ListView {
    public ListViewForScroll(Context context) {
        this(context, null);
    }

    public ListViewForScroll(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListViewForScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
