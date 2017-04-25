package com.agh.reminder.reminder.data_access.Interfaces;

import com.agh.reminder.reminder.models.Activity;

import java.sql.SQLException;
import java.util.List;

public interface IActivityDao {
    List<Activity> getActive() throws SQLException;
    void deleteByID(Integer id) throws SQLException;
    void create(Activity activity) throws SQLException;
}
