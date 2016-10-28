package com.example.li.demo.view.toutiao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.li.demo.R;

import java.util.ArrayList;
import java.util.List;

public class ScrollTopActivity extends AppCompatActivity {
    ChildView childView;
    List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_top);

        childView = (ChildView) findViewById(R.id.tv);

       // list = Arrays.asList(strings);  // TODO: 2016/10/19 注意asList不能进行add和remove操作
        list = new ArrayList<>();
        list.add("需要插值器");
        list.add("y轴偏移量如何计算");
        list.add("能够平滑滚动");
        list.add("从底部到顶部");

        childView.setData(list);
        childView.setClickListener((v, o) ->
                Toast.makeText(ScrollTopActivity.this, o, Toast.LENGTH_SHORT).show());
    }
}
