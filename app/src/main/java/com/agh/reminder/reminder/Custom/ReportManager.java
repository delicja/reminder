package com.agh.reminder.reminder.Custom;

import com.agh.reminder.reminder.data_access.DatabaseHelper;
import com.agh.reminder.reminder.models.Activity;
import com.agh.reminder.reminder.models.ActivityResults;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ReportManager {

    private DatabaseHelper databaseHelper;

    public ReportManager(DatabaseHelper databaseHelper){
        this.databaseHelper = databaseHelper;
    }

    public String prepareReportForCurrentDay(){
        Date today = new Date();
        return prepareReportForDateRange(Utils.getMinDate(today), Utils.getMaxDate(today));
    }

    public String prepareReportForCurrentWeek(){
        Date weekStart = Utils.getFirstDayOfWeek();
        Date weekEnd = Utils.getLastDayOfWeek();
        return prepareReportForDateRange(weekStart, weekEnd);
    }

    public String prepareReportForCurrentMonth(){
        Date monthStart = Utils.getFirstDayOfMonth();
        Date monthEnd = Utils.getLastDayOfMonth();
        return prepareReportForDateRange(monthStart, monthEnd);
    }

    public String prepareReportForDateRange(Date from, Date to){
        try {
            List<ActivityResults> results = databaseHelper.getActivityResultDao().getByDateRange(from, to);
            return createReport(results);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String createReport(List<ActivityResults> activityResults) {
        return null;
    }
}
