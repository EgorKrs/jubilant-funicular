package com.loneliness.service.common_service;


import com.loneliness.dao.DAOException;
import com.loneliness.dao.WorkWithUserDAO;
import com.loneliness.entity.Entity;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReceiveByUserIdService<T extends Entity> implements Service<T, T, Integer, T> {
    private final WorkWithUserDAO<T> dao;
    private Logger logger = LogManager.getLogger();

    public ReceiveByUserIdService(WorkWithUserDAO<T> dao) {
        this.dao = dao;
    }

    @Override
    public T execute(Integer data) throws ServiceException {
        try {
            return dao.receiveByUserId(data);
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public T undo(T note) {
        return null;
    }
}
