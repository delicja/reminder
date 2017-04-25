package com.agh.reminder.reminder.data_access;

import com.agh.reminder.reminder.data_access.Interfaces.IActivityDao;
import com.agh.reminder.reminder.models.Activity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class ActivityDao extends BaseDao<Activity> implements IActivityDao {

    public ActivityDao(Dao<Activity, Integer> activityDao){
        super(activityDao);
    }

    @Override
    public List<Activity> getActive() throws SQLException {
        return InternalDao.queryBuilder().where().eq("active", true).query();
    }

    @Override
    public void deleteByID(Integer id) throws SQLException {
        InternalDao.deleteById(id);
    }

    @Override
    public void create(Activity activity) throws SQLException {
        InternalDao.create(activity);
    }
}
