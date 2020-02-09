package com.loneliness.service.common_service;

import com.loneliness.dao.DAO;
import com.loneliness.dao.DAOException;
import com.loneliness.dao.FactoryDAO;
import com.loneliness.entity.Entity;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateService<T extends Entity> implements Service<Integer, Integer, T, Integer> {
    private final DAO<T> dao;
    private Logger logger = LogManager.getLogger();

    public CreateService(DAO<T> dao) {
        this.dao = dao;
    }


    public CreateService(Class<T> tClass) throws ServiceException {
        try {
            dao = FactoryDAO.getInstance().getDao(tClass);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Integer execute(T note) throws ServiceException {
        try {
            return dao.create(note);
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Integer undo(Integer note) throws ServiceException {
        try {
            return dao.delete(note);
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }
}
