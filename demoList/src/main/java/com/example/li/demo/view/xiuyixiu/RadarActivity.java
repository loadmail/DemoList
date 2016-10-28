package com.example.li.demo.view.xiuyixiu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.li.demo.R;

public class RadarActivity extends AppCompatActivity {


    private RadarLayout layout1;
    private RadarLayout layout2;
    private RadarLayout layout3;
    private RadarLayout layout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);
        layout1 = (RadarLayout) findViewById(R.id.radarlayout1);

        layout2 = (RadarLayout) findViewById(R.id.radarlayout2);
        layout2.setUseRing(true);
        layout2.setCount(2);

        layout3 = (RadarLayout) findViewById(R.id.radarlayout3);
        layout3.setUseRing(false);
        layout3.setColor(Color.RED);

        layout4 = (RadarLayout) findViewById(R.id.radarlayout4);
        layout4.setCount(7);
        layout4.setColor(Color.BLUE);
        layout4.setUseRing(true);
    }

    public void start(View view) {
        layout1.start();
        layout2.start();
        layout3.start();
        layout4.start();
    }
}
