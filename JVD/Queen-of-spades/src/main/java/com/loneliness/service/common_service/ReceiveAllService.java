package com.loneliness.service.common_service;

import com.loneliness.dao.DAO;
import com.loneliness.dao.DAOException;
import com.loneliness.entity.Entity;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ReceiveAllService<T extends Entity, P extends Entity> implements Service<Collection<T>, Collection<T>, T, P> {
    private final DAO<T> dao;
    private Logger logger = LogManager.getLogger();

    public ReceiveAllService(DAO<T> dao) {
        this.dao = dao;
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
    public Collection<T> undo(P note) {
        return new ConcurrentLinkedQueue<>();
    }
}
