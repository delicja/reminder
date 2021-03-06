package com.agh.reminder.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.agh.reminder.reminder.data_access.DatabaseHelper;
import com.agh.reminder.reminder.data_access.Interfaces.IActivityDao;
import com.agh.reminder.reminder.models.Activity;
import com.agh.reminder.reminder.models.ActivityResults;
import com.agh.reminder.reminder.models.Stopwatch;

import java.sql.SQLException;
import java.util.Date;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity;
    private Button buttonStart, buttonStop, buttonPause;

    private boolean pause = false;
    private final Handler handler = new Handler();
    private Runnable callback;

    private Stopwatch stopwatch;

    private DatabaseHelper databaseHelper;
    private IActivityDao activityDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        stopwatch = new Stopwatch();

        buttonStart = (Button) findViewById(R.id.button);
        buttonPause = (Button) findViewById(R.id.button2);
        buttonStop = (Button) findViewById(R.id.button3);

        buttonStop.setEnabled(false);
        buttonPause.setEnabled(false);

        databaseHelper = new DatabaseHelper(this);

        try {
            activityDao = databaseHelper.getActivityDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final TextView textView = (TextView) findViewById(R.id.textView);
        final TextView title = (TextView) findViewById(R.id.title);
        final TextView description = (TextView) findViewById(R.id.description);

        //TODO: Android ImageButton!!!
        //TODO: first only start
        //after start -> pause
        //after pause -> resume or reset
        buttonStart.setOnClickListener(this);
        buttonPause.setOnClickListener(this);
        buttonStop.setOnClickListener(this);

        callback = new Runnable() {
            @Override
            public void run() {
                stopwatch.startStopwatch();
                textView.setText(stopwatch.getHumanReadableTime());
                handler.postDelayed(this, 1000);
            }
        };


        Intent intent = getIntent();
        String nameActivity = intent.getStringExtra("name");
        String descriptionActivity = intent.getStringExtra("description");
        title.setText(nameActivity);
        description.setText(descriptionActivity);
        int id = intent.getIntExtra("id", 0);

        try {
            activity = activityDao.getById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                start();
                break;
            case R.id.button2:
                pause = true;
                stop(false);
                break;
            case R.id.button3:
                pause = false;
                stop(true);
                break;
        }
    }


    private void stop(boolean showReport) {
        stopwatch.stopStopwatch();

        handler.removeCallbacks(callback);
        buttonStart.setEnabled(true);
        if (pause) {
            buttonStop.setEnabled(true);
        }
        buttonPause.setEnabled(false);
        ActivityResults activityResults = new ActivityResults();
        activityResults.setActivityId(activity.getId());
        activityResults.setTimeSpent(stopwatch.getTime() / 60);
        activityResults.setDate(new Date());


        if (showReport) {
            try {
                databaseHelper.getActivityResultDao().createResult(activityResults);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    private void start() {
        if (!pause) stopwatch.resetStopwatch();
        handler.post(callback);
        stopwatch.resumeStopwatch();
        buttonStart.setEnabled(false);
        buttonStop.setEnabled(true);
        buttonPause.setEnabled(true);
    }
}
