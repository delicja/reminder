package com.agh.reminder.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        final CheckBox isActive = (CheckBox) findViewById(R.id.checkBox2);
        isActive.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        final int id = intent.getIntExtra("id", 0);
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

                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("editedActivityId", activity.getId());
                    startActivity(intent);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
