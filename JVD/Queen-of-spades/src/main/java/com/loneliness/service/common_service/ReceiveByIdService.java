package com.loneliness.service.common_service;

import com.loneliness.dao.DAO;
import com.loneliness.dao.DAOException;
import com.loneliness.entity.Entity;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReceiveByIdService<T extends Entity> implements Service<T, T, T, T> {
    private final DAO<T> dao;
    private T data;
    private Logger logger = LogManager.getLogger();

    public ReceiveByIdService(DAO<T> dao) {
        this.dao = dao;
    }

    @Override
    public T execute(T data) throws ServiceException {
        try {
            this.data = data;
            return dao.receive(data);
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public T undo(T note) {
        return data;
    }
}
