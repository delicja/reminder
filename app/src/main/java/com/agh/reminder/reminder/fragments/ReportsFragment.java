package com.agh.reminder.reminder.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.agh.reminder.reminder.MainActivity;
import com.agh.reminder.reminder.R;
import com.agh.reminder.reminder.custom.ReportManager;
import com.agh.reminder.reminder.data_access.DatabaseHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class ReportsFragment extends Fragment {

    private DatabaseHelper databaseHelper;

    private RadioButton todayRadioButton;
    private RadioButton lastWeekRadioButton;
    private RadioButton lastMonthRadioButton;
    private RadioButton customRadioButton;
    private LinearLayout selectCustomDatePanel;
    private LinearLayout reportPanel;
    private Button generateReportButton;
    private Button selectStartDateButton;
    private Button selectEndDateButton;
    private TextView reportContentTextView;
    private EditText startDateEditText;
    private EditText endDateEditText;

    private Date startDate;
    private Date endDate;

    private ReportManager reportManager;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private DatePickerDialog datePickerDialog;
    private Boolean selectingStartDate;
    private Boolean selectingEndDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        databaseHelper = ((MainActivity)getActivity()).getDatabaseHelper();
        reportManager = new ReportManager(databaseHelper);
        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(selectingStartDate) {
                    selectStartDate(year, month, dayOfMonth);
                } else if(selectingEndDate) {
                    selectEndDate(year, month, dayOfMonth);
                }
            }
        }, 2017, 5, 13);

        View view = inflater.inflate(R.layout.report, container, false);

        todayRadioButton = (RadioButton)view.findViewById(R.id.todayRadioBtn);
        lastWeekRadioButton = (RadioButton)view.findViewById(R.id.lastWeekRadioBtn);
        lastMonthRadioButton = (RadioButton)view.findViewById(R.id.lastMonthRadioBtn);
        customRadioButton = (RadioButton)view.findViewById(R.id.customRadioBtn);
        selectCustomDatePanel = (LinearLayout)view.findViewById(R.id.selectCustomDatePanel);
        reportPanel = (LinearLayout)view.findViewById(R.id.reportPanel);
        generateReportButton = (Button)view.findViewById(R.id.generateReportButton);
        selectStartDateButton = (Button) view.findViewById(R.id.selectStartDateButton);
        selectEndDateButton = (Button) view.findViewById(R.id.selectEndDateButton);
        reportContentTextView = (TextView)view.findViewById(R.id.reportContentTextView);
        startDateEditText = (EditText)view.findViewById(R.id.startDateEditText);
        endDateEditText = (EditText)view.findViewById(R.id.endDateEditText);

        customRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    selectCustomDatePanel.setVisibility(View.VISIBLE);
                } else {
                    selectCustomDatePanel.setVisibility(View.INVISIBLE);
                }
            }
        });

        generateReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateReport();
            }
        });

        selectStartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectingStartDate = true;
                selectingEndDate = false;
                datePickerDialog.show();
            }
        });

        selectEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectingStartDate = false;
                selectingEndDate = true;
                datePickerDialog.show();
            }
        });

        return view;
    }

    private void generateReport(){
        if(selectCustomDatePanel.getVisibility() == View.VISIBLE) {
            selectCustomDatePanel.setVisibility(View.INVISIBLE);
        }

        String report = "";
        if(todayRadioButton.isChecked()) {
            report = reportManager.prepareReportForCurrentDay();
        } else if (lastWeekRadioButton.isChecked()){
            report = reportManager.prepareReportForCurrentWeek();
        } else if(lastMonthRadioButton.isChecked()){
            report = reportManager.prepareReportForCurrentMonth();
        } else if(customRadioButton.isChecked()){
            if(startDate == null) {
                showAlert("Select start date");
                return;
            } else if (endDate == null) {
                showAlert("Select end date");
                return;
            } else {
                report = reportManager.prepareReportForDateRange(startDate, endDate);
            }
        }

        reportContentTextView.setText(null);
        reportPanel.setVisibility(View.VISIBLE);
        reportContentTextView.setText(report);
    }

    private void selectStartDate(int year, int month, int dayOfMonth) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(year, month, dayOfMonth);

        startDate = cal.getTime();
        startDateEditText.setText(dateFormat.format(startDate));
    }

    private void selectEndDate(int year, int month, int dayOfMonth) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(year, month, dayOfMonth);

        endDate = cal.getTime();
        endDateEditText.setText(dateFormat.format(endDate));
    }

    private void showAlert(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
    }
}
