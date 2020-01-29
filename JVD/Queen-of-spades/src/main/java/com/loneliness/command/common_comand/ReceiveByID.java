package com.loneliness.command.common_comand;

import com.loneliness.dao.DAO;
import com.loneliness.dao.DAOException;
import com.loneliness.command.Command;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReceiveByID<T> implements Command<T,T,T> {
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
