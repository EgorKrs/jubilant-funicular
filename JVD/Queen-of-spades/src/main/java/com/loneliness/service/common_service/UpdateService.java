package com.loneliness.service.common_service;

import com.loneliness.dao.DAO;
import com.loneliness.dao.DAOException;
import com.loneliness.dao.FactoryDAO;
import com.loneliness.entity.Entity;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateService<T extends Entity> implements Service<Integer, Integer, T, T> {
    private final Logger logger = LogManager.getLogger();
    private final DAO<T> dao;

    public UpdateService(DAO<T> dao) {
        this.dao = dao;
    }

    public UpdateService(Class<T> tClass) throws ServiceException {
        try {
            dao = FactoryDAO.getInstance().getDao(tClass);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Integer execute(T data) throws ServiceException {
        try {
            return dao.update(data);
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Integer undo(T data) throws ServiceException {
        try {
            return dao.update(data);
        } catch (DAOException | ClassCastException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }
}


