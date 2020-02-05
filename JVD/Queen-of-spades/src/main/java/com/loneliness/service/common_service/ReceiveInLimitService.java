package com.loneliness.service.common_service;

import com.loneliness.dao.DAO;
import com.loneliness.dao.DAOException;
import com.loneliness.entity.Entity;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

public class ReceiveInLimitService<E extends Entity, P> implements Service<E, Collection<E>, Integer[], P> {
    private final DAO<E> dao;
    private Logger logger = LogManager.getLogger();

    public ReceiveInLimitService(DAO<E> dao) {
        this.dao = dao;
    }

    @Override
    public Collection<E> execute(Integer[] data) throws ServiceException {
        try {
            return dao.receiveAll(data);
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public E undo(P note) throws ServiceException {
        return null;
    }
}
