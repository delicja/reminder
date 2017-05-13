package com.agh.reminder.reminder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        final CheckBox isActive = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox needsGps = (CheckBox) findViewById(R.id.checkBox3);
        final EditText time = (EditText)findViewById(R.id.timeEditText);

        Button addButton = (Button) findViewById(R.id.button4);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String timeStr = time.getText().toString();
                if(!tryParseInt(timeStr)){
                    Toast.makeText(AddActivity.this, "Provided time to spent is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                Activity activity = new Activity();
                activity.setName(name.getText().toString());
                activity.setDescription(description.getText().toString());
                activity.setActive(isActive.isChecked());
                activity.setNeedGps(needsGps.isChecked());
                activity.setAutoDetect(false);
                activity.setDefault(false);
                activity.setTime(Integer.parseInt(timeStr));

                try {
                    activityDao.create(activity);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent intent =new Intent(AddActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("addedActivityId", activity.getId());
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(this);

        try {
            activityDao = databaseHelper.getActivityDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
