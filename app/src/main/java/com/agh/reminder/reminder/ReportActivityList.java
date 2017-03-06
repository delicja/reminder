package com.agh.reminder.reminder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.agh.reminder.reminder.models.Report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportActivityList extends AppCompatActivity {

    private List<Report> reports = new ArrayList<>();
    private ListView reportList;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        reportList = (ListView) findViewById(R.id.listView);

        createReports();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, reports);
        reportList.setAdapter(adapter);
    }


    private void createReports() {
        Report r1 = new Report();
        r1.setId(1);
        r1.setDateFrom(new Date());
        r1.setDateTo(new Date());
        r1.setType(1);
        r1.setRead(false);

        Report r2 = new Report();
        r2.setId(1);
        r2.setDateFrom(new Date());
        r2.setDateTo(new Date());
        r2.setType(1);
        r2.setRead(false);

        reports.add(r1);
        reports.add(r2);
    }
}
