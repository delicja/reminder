package com.agh.reminder.reminder;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.agh.reminder.reminder.models.Activity;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity;
    private Button buttonStart, buttonStop, buttonPause;

    private int milliseconds;
    private boolean running;
    private boolean pause = false;
    private final Handler handler = new Handler();
    private Runnable callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        buttonStart = (Button) findViewById(R.id.button);
        buttonPause = (Button) findViewById(R.id.button2);
        buttonStop = (Button) findViewById(R.id.button3);

        buttonStop.setEnabled(false);
        buttonPause.setEnabled(false);

        final TextView textView = (TextView) findViewById(R.id.textView);
        final TextView textView2 = (TextView) findViewById(R.id.textView2);

        buttonStart.setOnClickListener(this);
        buttonPause.setOnClickListener(this);
        buttonStop.setOnClickListener(this);

        callback = new Runnable() {
            @Override
            public void run() {


                long seconds = (milliseconds % 3600) / 60;
                long minutes = (milliseconds / (1000 * 60)) % 60;
                long hours = milliseconds / (1000 * 60 * 60);

                String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                textView.setText(time);
                textView2.setText(String.valueOf(milliseconds % 60));
                if (running) {
                    milliseconds++;
                }
                handler.postDelayed(this, 0);
            }
        };

        createActivity();
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
        running = false;
        handler.removeCallbacks(callback);
        buttonStart.setEnabled(true);
        buttonStop.setEnabled(false);
        buttonPause.setEnabled(false);
        activity.setTime(milliseconds % 60);


        Toast.makeText(this.getApplicationContext(), Integer.toString(activity.getTime()), Toast.LENGTH_LONG).show();
    }

    private void start() {
        //3600000
        if (!pause) milliseconds = 3599900;
        handler.post(callback);
        running = true;
        buttonStart.setEnabled(false);
        buttonStop.setEnabled(true);
        buttonPause.setEnabled(true);
    }

    private void createActivity() {
        activity = new Activity();
        activity.setId(1);
        activity.setName("Sprzątanie");
        activity.setDescription("Wiosenne porządki czas zacząć");
        activity.setActive(true);
        activity.setAutoDetect(false);
        activity.setDefault(false);
    }

}
