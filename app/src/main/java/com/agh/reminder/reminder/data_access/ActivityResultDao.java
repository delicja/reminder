package com.agh.reminder.reminder.data_access;


import com.agh.reminder.reminder.custom.Utils;
import com.agh.reminder.reminder.data_access.Interfaces.IActivityResultDao;
import com.agh.reminder.reminder.models.Activity;
import com.agh.reminder.reminder.models.ActivityResults;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

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
        return InternalDao.queryBuilder().where().between("dateFrom", from, to).query();
    }

    @Override
    public void createResult(Integer activityId, Date from, Date to, int timeSpent) throws SQLException {
        ActivityResults result = new ActivityResults();
        result.setActivityId(activityId);
        result.setDateFrom(from);
        result.setDateTo(to);
        result.setTimeSpent(timeSpent);

        createResult(result);
    }

    @Override
    public void createResult(Integer activityId, Date from, Date to, int timeSpent, double gpsDistance) throws SQLException {
        ActivityResults result = new ActivityResults();
        result.setActivityId(activityId);
        result.setDateFrom(from);
        result.setDateTo(to);
        result.setGpsDistance(gpsDistance);
        result.setTimeSpent(timeSpent);

        createResult(result);
    }

    @Override
    public void createResult(ActivityResults result) throws SQLException {
        InternalDao.create(result);
    }

    @Override
    public void prepareDefaultData(List<Activity> activities) throws SQLException {
        Date startOfTheMonth = Utils.getFirstDayOfMonth();
        Date endOfTheMonth = Utils.getLastDayOfMonth();




    }
}
