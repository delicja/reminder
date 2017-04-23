package com.agh.reminder.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.agh.reminder.reminder.models.Activity;
import com.agh.reminder.reminder.models.ActivityResults;
import com.agh.reminder.reminder.models.Stopwatch;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity;
    private Button buttonStart, buttonStop, buttonPause;

    private boolean pause = false;
    private final Handler handler = new Handler();
    private Runnable callback;

    private Stopwatch stopwatch;

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


        Intent i = getIntent();
        String nameActivity = i.getStringExtra("name");
        String descriptionActivity = i.getStringExtra("description");

        title.setText(nameActivity);
        description.setText(descriptionActivity);

        createActivity(nameActivity, descriptionActivity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                start();
                break;
            case R.id.button2:
                pause = true;
                stop();
                break;
            case R.id.button3:
                pause = false;
                stop();
                break;
        }
    }


    private void stop() {
        stopwatch.stopStopwatch();

        handler.removeCallbacks(callback);
        buttonStart.setEnabled(true);
        if (pause) buttonStop.setEnabled(true);
        buttonPause.setEnabled(false);
        activity.setTime(stopwatch.getTime());
        ActivityResults activityResults = new ActivityResults();
        activityResults.setActivityId(activity.getId());
        activityResults.setTimeSpent(activityResults.getTimeSpent() + activity.getTime());

        Intent intent = new Intent(this, ReportActivityList.class);
        intent.putExtra("name", activity.getName());
        intent.putExtra("description", activity.getDescription());
        intent.putExtra("time", String.valueOf(activity.getTime()));
        startActivity(intent);

        Toast.makeText(this.getApplicationContext(), Integer.toString(activity.getTime()), Toast.LENGTH_LONG).show();
    }

    private void start() {
        if (!pause) stopwatch.resetStopwatch();
        handler.post(callback);
        stopwatch.resumeStopwatch();
        buttonStart.setEnabled(false);
        buttonStop.setEnabled(true);
        buttonPause.setEnabled(true);
    }

    private void createActivity(String name, String description) {
        activity = new Activity();
        activity.setId(1);
        activity.setName(name);
        activity.setDescription(description);
        activity.setActive(true);
        activity.setAutoDetect(false);
        activity.setDefault(false);
    }

}
