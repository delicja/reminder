package com.agh.reminder.reminder.data_access;

import com.agh.reminder.reminder.data_access.Interfaces.IActivityDao;
import com.agh.reminder.reminder.models.Activity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
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

    @Override
    public boolean InitializeDefaultActivities() {
        try {
            Activity swimming = prepareActivity("Pływanie", "Pływanie", false, false, 30);
            Activity reading = prepareActivity("Czytanie", "Pływanie", false, false, 30);
            Activity running = prepareActivity("Bieganie", "Pływanie", false, false, 30);
            Activity yoga = prepareActivity("Yoga", "Pływanie", false, false, 30);
            Activity gaming = prepareActivity("Granie na komputerze", "Pływanie", false, false, 30);
            Activity football = prepareActivity("Granie w piłkę nożną", "Pływanie", false, false, 30);

            create(swimming);
            create(reading);
            create(running);
            create(yoga);
            create(gaming);
            create(football);

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Activity getById(Integer id) throws SQLException {
        return InternalDao.queryForId(id);
    }

    private Activity prepareActivity(String name, String description, boolean needsGps, boolean autoDetect, int time){
        Activity activity = new Activity();
        activity.setActive(true);
        activity.setDefault(true);
        activity.setName(name);
        activity.setDescription(description);
        activity.setNeedGps(needsGps);
        activity.setAutoDetect(autoDetect);
        activity.setTime(time);
        return activity;
    }
}
