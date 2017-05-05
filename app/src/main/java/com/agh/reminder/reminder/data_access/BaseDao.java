package com.agh.reminder.reminder.data_access;

import com.agh.reminder.reminder.data_access.Interfaces.IBaseDao;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;


public abstract class BaseDao<TEntity> implements IBaseDao<TEntity> {

    protected Dao<TEntity, Integer> InternalDao;

    public BaseDao(Dao<TEntity, Integer> dao){
        InternalDao = dao;
    }

    @Override
    public List<TEntity> getAll() throws SQLException {
        return InternalDao.queryForAll();
    }

    @Override
    public TEntity getById(Integer id) throws SQLException {
        return InternalDao.queryForId(id);
    }

    @Override
    public void insert(TEntity entity) throws SQLException {
        InternalDao.create(entity);
    }

    @Override
    public void update(TEntity entity) throws SQLException {
        InternalDao.update(entity);
    }
}
