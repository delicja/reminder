package com.agh.reminder.reminder.data_access;

import com.agh.reminder.reminder.Custom.Resources;
import com.agh.reminder.reminder.data_access.Interfaces.IConfigurationDao;
import com.agh.reminder.reminder.models.Configuration;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class ConfigurationDao extends BaseDao<Configuration> implements IConfigurationDao {



    public ConfigurationDao(Dao<Configuration, Integer> dao) {
        super(dao);
    }

    public boolean getIsDefaultDataInitialized(){
        Configuration isInitialized = getConfiguration(Resources.IsInitializedConfigurationKey);
        return isInitialized == null ? false : Boolean.valueOf(isInitialized.getValue());
    }

    @Override
    public Configuration getConfiguration(String key) {
        try {
            List<Configuration> values = InternalDao.queryForEq("Key", key);

            if(values.isEmpty()){
                return null;
            }
            return values.get(0);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void insertConfiguration(String key, String value) {
        Configuration configuration = new Configuration();
        configuration.setKey(key);
        configuration.setValue(value);

        insertConfiguration(configuration);
    }

    @Override
    public void insertConfiguration(Configuration configuration) {
        try {
            InternalDao.create(configuration);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateConfiguration(String key, String value) {
        Configuration configuration = new Configuration();
        configuration.setKey(key);
        configuration.setValue(value);

        updateConfiguration(configuration);
    }

    @Override
    public void updateConfiguration(Configuration configuration) {
        Configuration existingEntity = getConfiguration(configuration.getKey());
        if(existingEntity == null){
            insertConfiguration(configuration);
        }
        else{
            try {
                InternalDao.update(configuration);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
