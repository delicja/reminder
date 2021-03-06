package com.agh.reminder.reminder.data_access.Interfaces;

import com.agh.reminder.reminder.models.Activity;
import com.agh.reminder.reminder.models.ActivityResults;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IActivityResultDao {
    List<ActivityResults> getByDate(Date date) throws SQLException;
    List<ActivityResults> getByDateRange(Date from, Date to) throws SQLException;

    void createResult(Integer activityId, Date date, int timeSpent) throws SQLException;
    void createResult(Integer activityId, Date date, int timeSpent, double gpsDistance) throws SQLException;
    void createResult(ActivityResults result) throws SQLException;

    long countTimeSpentForToday(Integer activityId) throws SQLException;

    void prepareDefaultData(List<Activity> activities) throws SQLException;

    void update(ActivityResults result) throws SQLException;
    void deleteForActivity(Integer activityId) throws SQLException;
}
