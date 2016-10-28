package com.example.parcelabledemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent i = getIntent();
        Person p = i.getParcelableExtra("yes");
        System.out.println("---->" + p.name);
        System.out.println("---->" + p.map.size());

    }
}
