package com.loneliness.service.common_service;

import com.loneliness.dao.DAO;
import com.loneliness.dao.DAOException;
import com.loneliness.entity.Entity;
import com.loneliness.service.Command;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReceiveByID<T extends Entity> implements Command<T,T,T> {
    private T data;
    private Logger logger = LogManager.getLogger();
    private final DAO<T> dao;

    public ReceiveByID(DAO<T> dao) {
        this.dao=dao;
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
    public T undo() {
        return data;
    }
}
