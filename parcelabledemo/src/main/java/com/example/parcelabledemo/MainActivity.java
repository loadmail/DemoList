package com.example.parcelabledemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        Person p = new Person();
        p.map = new HashMap<String,String>();
        p.map.put("yes", "ido");
        p.name="ok";
        intent.putExtra("yes", p);
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }
}
