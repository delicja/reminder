package com.agh.reminder.reminder.data_access;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.agh.reminder.reminder.Custom.Resources;
import com.agh.reminder.reminder.data_access.Interfaces.IActivityDao;
import com.agh.reminder.reminder.data_access.Interfaces.IActivityResultDao;
import com.agh.reminder.reminder.data_access.Interfaces.IConfigurationDao;
import com.agh.reminder.reminder.models.Activity;
import com.agh.reminder.reminder.models.ActivityResults;
import com.agh.reminder.reminder.models.Configuration;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "reminder.db";
    private static final int DATABASE_VERSION = 1;

    private IActivityDao _activityDao;
    private IActivityResultDao _activityResultsDao;
    private IConfigurationDao _configurationDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public IActivityDao getActivityDao() throws SQLException {
        if(_activityDao == null){
            Dao<Activity, Integer> dao = getDao(Activity.class);
            _activityDao = new ActivityDao(dao);
        }
        return _activityDao;
    }

    public IActivityResultDao getActivityResultDao() throws SQLException {
        if(_activityResultsDao == null){
            Dao<ActivityResults, Integer> dao = getDao(ActivityResults.class);
            _activityResultsDao = new ActivityResultDao(dao);
        }
        return _activityResultsDao;
    }

    public IConfigurationDao getConfigurationDao() throws SQLException {
        if(_activityResultsDao == null){
            Dao<Configuration, Integer> dao = getDao(Configuration.class);
            _configurationDao = new ConfigurationDao(dao);
        }
        return _configurationDao;
    }

    public void EnsureDefaultDataInitialized(){
        try {
            IConfigurationDao configurationDao = getConfigurationDao();

            boolean defaultDataInitialized = configurationDao.getIsDefaultDataInitialized();
            if(defaultDataInitialized){
                return;
            }

            IActivityDao activityDao = getActivityDao();
            boolean dataInitialized = activityDao.InitializeDefaultActivities();

            Configuration isDefaultDataInitialized = new Configuration();
            isDefaultDataInitialized.setKey(Resources.IsInitializedConfigurationKey);
            isDefaultDataInitialized.setValue(String.valueOf(dataInitialized));

            configurationDao.insertConfiguration(isDefaultDataInitialized);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Activity.class);
            TableUtils.createTable(connectionSource, ActivityResults.class);
            TableUtils.createTable(connectionSource, Configuration.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Activity.class, true);
            TableUtils.dropTable(connectionSource, ActivityResults.class, true);
            TableUtils.dropTable(connectionSource, Configuration.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }
}