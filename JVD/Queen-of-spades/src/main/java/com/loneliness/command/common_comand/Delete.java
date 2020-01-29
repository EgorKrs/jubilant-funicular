package com.loneliness.command.common_comand;

import com.loneliness.dao.DAO;
import com.loneliness.dao.DAOException;
import com.loneliness.command.Command;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Delete<T> implements Command<Integer,Integer,  T> {
    private T data;
    private Logger logger = LogManager.getLogger();
    private final DAO<T> dao;

    public Delete(DAO<T> dao) {
        this.dao=dao;
    }

    @Override
    public Integer execute(T data) throws ServiceException {
        try {
            this.data =data;
            return dao.delete(data);
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public Integer undo() throws ServiceException {
        try {
            return dao.create(data);
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(),e.getCause());
        }
    }
}
