package com.agh.reminder.reminder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.agh.reminder.reminder.models.Activity;

public class StartActivity extends AppCompatActivity {

    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

}
