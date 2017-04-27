package com.agh.reminder.reminder.data_access.Interfaces;

import com.agh.reminder.reminder.models.Configuration;

public interface IConfigurationDao {
    boolean getIsDefaultDataInitialized();

    Configuration getConfiguration(String key);

    void insertConfiguration(String key, String value);
    void insertConfiguration(Configuration configuration);
    void updateConfiguration(String key, String value);
    void updateConfiguration(Configuration configuration);
}
