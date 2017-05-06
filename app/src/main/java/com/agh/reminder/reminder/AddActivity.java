package com.agh.reminder.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.agh.reminder.reminder.data_access.DatabaseHelper;
import com.agh.reminder.reminder.data_access.Interfaces.IActivityDao;
import com.agh.reminder.reminder.models.Activity;

import java.sql.SQLException;

public class AddActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private IActivityDao activityDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final EditText name = (EditText) findViewById(R.id.editText3);
        final EditText description = (EditText) findViewById(R.id.editText4);
        //final CheckBox isActive = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox needsGps = (CheckBox) findViewById(R.id.checkBox3);

        Button addButton = (Button) findViewById(R.id.button4);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Activity activity = new Activity();
                activity.setName(name.getText().toString());
                activity.setDescription(description.getText().toString());
                activity.setActive(true);
                activity.setNeedGps(needsGps.isEnabled());
                activity.setAutoDetect(false);
                activity.setDefault(false);
                activity.setTime(0);

                try {
                    activityDao.create(activity);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent i=new Intent(AddActivity.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        databaseHelper = new DatabaseHelper(this);

        try {
            activityDao = databaseHelper.getActivityDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
