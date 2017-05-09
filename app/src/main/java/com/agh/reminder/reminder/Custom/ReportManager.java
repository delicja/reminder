package com.agh.reminder.reminder.Custom;

import com.agh.reminder.reminder.data_access.DatabaseHelper;
import com.agh.reminder.reminder.models.Activity;
import com.agh.reminder.reminder.models.ActivityResults;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
            return createReport(results, getDateDiff(from, to, TimeUnit.DAYS));
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    private String createReport(List<ActivityResults> activityResults, long days) throws SQLException {
        HashMap<Integer, Activity> activityDictionary = new HashMap<>();
        HashMap<Integer, List<ActivityResults>> resultsGrouped = new HashMap<>();

        for (ActivityResults result:activityResults){
            Integer activityId = result.getActivityId();

            if(!activityDictionary.containsKey(activityId)){
                Activity activity = databaseHelper.getActivityDao().getById(activityId);
                activityDictionary.put(activityId, activity);
            }

            List<ActivityResults> group;
            if(resultsGrouped.containsKey(activityId)){
                group = resultsGrouped.get(activityId);
            } else{
                group = new ArrayList<>();
                resultsGrouped.put(activityId, group);
            }
            group.add(result);
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, List<ActivityResults>> resultGroup: resultsGrouped.entrySet()) {
            Activity activity = activityDictionary.get(resultGroup.getKey());

            long expectedTime = activity.getTime() * days;
            long calculatedTime = 0L;
            Double kilometers = null;
            for (ActivityResults result: resultGroup.getValue()) {
                calculatedTime += result.getTimeSpent();
                if(activity.isNeedGps()) {
                    kilometers += (result.getGpsDistance() / 1000);
                }
            }

            stringBuilder.append("Activity: " + activity.getName());
            stringBuilder.append("\nDescription " + activity.getDescription());

            stringBuilder.append("\nExpected time: " + String.valueOf(expectedTime) + ", time spent: " + String.valueOf(calculatedTime));

            if(activity.isNeedGps() && kilometers != null){
                stringBuilder.append("\nKilometers: " + String.valueOf(kilometers));
            }
        }
        return stringBuilder.toString();
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillisec = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillisec, TimeUnit.MILLISECONDS);
    }
}
