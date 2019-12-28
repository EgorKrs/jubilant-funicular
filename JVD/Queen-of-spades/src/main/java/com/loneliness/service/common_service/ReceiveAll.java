package com.loneliness.service.common_service;

import com.loneliness.dao.DAO;
import com.loneliness.dao.DAOException;
import com.loneliness.entity.Entity;
import com.loneliness.service.Command;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ReceiveAll <T extends Entity> implements Command<Collection<T>, Collection<T>,T> {
    private Logger logger = LogManager.getLogger();
    private final DAO<T> dao;

    public ReceiveAll(DAO<T> dao) {
        this.dao=dao;
    }

    @Override
    public  Collection<T> execute(T data) throws ServiceException {
        try {
           return dao.receiveAll();
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public Collection<T> undo() {
        return  new ConcurrentLinkedQueue<>();
    }
}
