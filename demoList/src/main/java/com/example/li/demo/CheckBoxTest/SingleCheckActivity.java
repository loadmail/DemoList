package com.example.li.demo.CheckBoxTest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.li.demo.R;


public class SingleCheckActivity extends Activity {

    MoreCheckBoxLayout reasonView;
    EditText feedbackEditText;
    String[] reasonStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackpage);
        InitView();
    }
    private void InitView() {
        reasonStrings = getResources().getStringArray(R.array.reasons);
        feedbackEditText = (EditText) findViewById(R.id.feedback_edit);
        reasonView = (MoreCheckBoxLayout) findViewById(R.id.reasons);
        reasonView.setReason(reasonStrings,feedbackEditText);

    }
}
