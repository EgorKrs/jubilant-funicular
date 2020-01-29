package com.loneliness.command.common_comand;

import com.loneliness.dao.DAO;
import com.loneliness.dao.DAOException;
import com.loneliness.entity.Entity;
import com.loneliness.command.Command;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Create<T extends Entity> implements Command<Integer,Integer,T> {
    private int id;
    private Logger logger = LogManager.getLogger();
    private final DAO<T> dao;

    public Create(DAO<T> dao) {
        this.dao=dao;
    }

    @Override
    public Integer execute(T data) throws ServiceException {
        try {
            int id= dao.create(data);
            this.id=id;
            return id;
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public Integer undo() throws ServiceException {
        try {
            return dao.delete(id);
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(),e.getCause());
        }
    }
}
