package com.example.winxin_ui;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onProgressUpdate(Object[] values) {
                super.onProgressUpdate(values);
            }
        };

        task.execute("params");
    }

}
