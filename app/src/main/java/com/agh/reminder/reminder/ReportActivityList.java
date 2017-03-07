package com.agh.reminder.reminder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.agh.reminder.reminder.models.Report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportActivityList extends AppCompatActivity {

    private List<Report> reports = new ArrayList<>();
    private ListView reportList;
    private static CustomReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        reportList = (ListView) findViewById(R.id.listView);

        createReports();

        adapter = new CustomReportAdapter(reports, getApplicationContext());
        reportList.setAdapter(adapter);

        reportList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Report report = reports.get(position);


                AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(ReportActivityList.this).setMessage(report.getDescription())
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                // Do stuff if user accepts
                            }



                        /*}).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                // Do stuff when user neglects.
                            }


                        }).setOnCancelListener(new DialogInterface.OnCancelListener() {

                            @Override
                            public void onCancel(DialogInterface dialog) {
                                dialog.dismiss();
                                // Do stuff when cancelled
                            }
                            */

                        }).create();
                dialog.show();

                report.setRead(true);
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), report.getDescription(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void createReports() {

        for (int i = 0; i < 40; i++) {
            Report r = new Report();
            r.setId(i + 1);
            r.setDescription(Long.toHexString(Double.doubleToLongBits(Math.random())));
            r.setDateFrom(new Date());
            r.setDateTo(new Date());
            r.setType(1);
            if (i % 3 == 0) r.setRead(false);
            else r.setRead(true);
            reports.add(r);
        }
    }
}
