package com.agh.reminder.reminder.data_access.Interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IBaseDao<TEntity> {
    List<TEntity> getAll() throws SQLException;
    TEntity getById(Integer id) throws SQLException;
    void insert(TEntity entity) throws SQLException;
    void update(TEntity entity) throws SQLException;
}
