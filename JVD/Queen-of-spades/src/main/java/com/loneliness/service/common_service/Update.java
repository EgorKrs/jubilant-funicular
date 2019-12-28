package com.loneliness.service.common_service;

import com.loneliness.dao.DAO;
import com.loneliness.dao.DAOException;
import com.loneliness.entity.Entity;
import com.loneliness.service.Command;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Update<T extends Entity> implements Command<Integer,Integer, T> {
    private T data;
    private final Logger logger = LogManager.getLogger();
    private final DAO<T> dao;

    public Update(DAO<T> dao) {
        this.dao=dao;
    }

    public Update setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public Integer execute(T data) throws ServiceException {
        try {

            return dao.update(data);
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public Integer undo() throws ServiceException {
        try {
            return dao.update(data);
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(),e.getCause());
        }
    }
}
