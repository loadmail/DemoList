package com.example.li.demo.MultiLevel.type;

import android.content.Context;
import android.view.View;

/**
 * Created by ly on 2016/11/1 13:46.
 */

public interface MultiType {
    int getLayout();

    boolean isCheckable();

    View getView(Context mContext, View convertView);
}
