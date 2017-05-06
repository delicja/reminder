package com.agh.reminder.reminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.agh.reminder.reminder.data_access.DatabaseHelper;
import com.agh.reminder.reminder.data_access.Interfaces.IActivityDao;
import com.agh.reminder.reminder.models.Activity;

import java.sql.SQLException;

public class EditActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private IActivityDao activityDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final EditText nameTxt = (EditText) findViewById(R.id.editText3);
        final EditText descriptionTxt = (EditText) findViewById(R.id.editText4);

        Intent intent = getIntent();
        final int id = Integer.parseInt(intent.getStringExtra("id"));
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");


        nameTxt.setText(name);
        descriptionTxt.setText(description);

        databaseHelper = new DatabaseHelper(this);
        try {
            activityDao = databaseHelper.getActivityDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Button addButton = (Button) findViewById(R.id.button4);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Activity activity = activityDao.getById(id);
                    activity.setName(nameTxt.getText().toString());
                    activity.setDescription(descriptionTxt.getText().toString());
                    activityDao.update(activity);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(EditActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
}
