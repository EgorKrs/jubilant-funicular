package com.loneliness.service.common_service;

import com.loneliness.dao.DAO;
import com.loneliness.dao.DAOException;
import com.loneliness.dao.FactoryDAO;
import com.loneliness.entity.Entity;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteService<T extends Entity, P extends Entity> implements Service<Integer, Integer, T, T> {
    private final DAO<T> dao;
    private Logger logger = LogManager.getLogger();

    public DeleteService(DAO<T> dao) {
        this.dao = dao;
    }

    public DeleteService(Class<T> tClass) throws ServiceException {
        try {
            dao = FactoryDAO.getInstance().getDao(tClass);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Integer execute(T data) throws ServiceException {
        try {
            return dao.delete(data);
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Integer undo(T data) throws ServiceException {
        try {
            return dao.create(data);
        } catch (DAOException | ClassCastException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }
}

