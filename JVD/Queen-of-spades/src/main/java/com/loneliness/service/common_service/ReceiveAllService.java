package com.loneliness.service.common_service;

import com.loneliness.dao.DAO;
import com.loneliness.dao.DAOException;
import com.loneliness.dao.FactoryDAO;
import com.loneliness.entity.Entity;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ReceiveAllService<T extends Entity> implements Service<Collection<T>, Collection<T>, T, T> {
    private final DAO<T> dao;
    private Logger logger = LogManager.getLogger();

    public ReceiveAllService(DAO<T> dao) {
        this.dao = dao;
    }

    public ReceiveAllService(Class<T> tClass) throws ServiceException {
        try {
            dao = FactoryDAO.getInstance().getDao(tClass);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Collection<T> execute(T data) throws ServiceException {
        try {
            return dao.receiveAll();
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Collection<T> undo(T note) {
        return new ConcurrentLinkedQueue<>();
    }
}
