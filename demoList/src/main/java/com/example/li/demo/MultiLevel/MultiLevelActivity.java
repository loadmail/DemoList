package com.example.li.demo.MultiLevel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.li.demo.MultiLevel.type.BottomMultiType;
import com.example.li.demo.MultiLevel.type.MiddleMultiType;
import com.example.li.demo.MultiLevel.type.MultiType;
import com.example.li.demo.MultiLevel.type.TopMultiType;
import com.example.li.demo.R;

import java.util.ArrayList;
import java.util.List;

public class MultiLevelActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<MultiType> multiTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_level);


        initData();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new MultiLevelAdapter(this, multiTypes));
    }

    private void initData() {
        final int GROUP_SIZE = 10;
        multiTypes = new ArrayList<>();


        for (int k = 0; k < GROUP_SIZE; k++) {
            List<Goods> eachList = new ArrayList<>();

            for (int i = 0; i < k + 1; i++) {
                Goods goods = new Goods();
                goods.setGoodName("商品" + i);
                goods.setGoodSn("商品SN" + i);
                eachList.add(goods);
            }
            TopMultiType top = new TopMultiType(k);
            multiTypes.add(top);

            for (int j = 0; j < eachList.size(); j++) {
                Goods goods = new Goods();
                //需要的数据直接传
                goods.setGoodName(eachList.get(j).getGoodName());
                goods.setGoodSn(eachList.get(j).getGoodSn());


                MiddleMultiType middle = new MiddleMultiType(goods);
                multiTypes.add(middle);
            }

            BottomMultiType bottom = new BottomMultiType(k);
            multiTypes.add(bottom);

        }
    }
}
