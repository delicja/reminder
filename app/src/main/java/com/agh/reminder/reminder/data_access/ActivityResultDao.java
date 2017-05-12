package com.agh.reminder.reminder.data_access;


import android.util.Log;

import com.agh.reminder.reminder.custom.Utils;
import com.agh.reminder.reminder.data_access.Interfaces.IActivityResultDao;
import com.agh.reminder.reminder.models.Activity;
import com.agh.reminder.reminder.models.ActivityResults;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ActivityResultDao extends BaseDao<ActivityResults> implements IActivityResultDao {

    protected ActivityResultDao(Dao<ActivityResults, Integer> dao) {
        super(dao);
    }

    @Override
    public List<ActivityResults> getByDate(Date date) throws SQLException {
        return getByDateRange(Utils.getMinDate(date), Utils.getMaxDate(date));
    }

    @Override
    public List<ActivityResults> getByDateRange(Date from, Date to) throws SQLException {
        return InternalDao.queryBuilder().where().between("date", from, to).query();
    }

    @Override
    public void createResult(Integer activityId, Date date, int timeSpent) throws SQLException {
        ActivityResults result = new ActivityResults();
        result.setActivityId(activityId);
        result.setDate(date);
        result.setTimeSpent(timeSpent);

        createResult(result);
    }

    @Override
    public void createResult(Integer activityId, Date date, int timeSpent, double gpsDistance) throws SQLException {
        ActivityResults result = new ActivityResults();
        result.setActivityId(activityId);
        result.setDate(date);
        result.setGpsDistance(gpsDistance);
        result.setTimeSpent(timeSpent);

        createResult(result);
    }

    @Override
    public void createResult(ActivityResults result) throws SQLException {
        InternalDao.create(result);
    }

    @Override
    public long countTimeSpentForToday(Integer activityId) throws SQLException {

        List<ActivityResults> results = InternalDao.queryBuilder().where()
                                                   .eq("activityId", activityId).and()
                                                   .between("date", Utils.getMinDate(new Date()), Utils.getMaxDate(new Date()))
                                                   .query();
        long sum = 0;
        for (ActivityResults result :
                results) {
            sum += result.getTimeSpent();
        }
        return sum;
    }

    @Override
    public void prepareDefaultData(List<Activity> activities) throws SQLException {
        for (int day = 1; day <= 12; day++) {
            for (Activity activity : activities) {
                createRandomResult(activity, day, Utils.randomBetween(15, 50));
            }
        }
    }

    private void createRandomResult(Activity activity, int day, int resultsCount) {
        for (int i = 0; i <= resultsCount; i++) {
            Date randomDateForDay = Utils.getRandomDateForDay(day);
            try {
                int a = (int)(activity.getTime() / resultsCount);
                createResult(activity.getId(), randomDateForDay,
                             Utils.randomBetween(a, Utils.randomBetween(a, activity.getTime())),
                             Utils.randomBetween(1, 200));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
