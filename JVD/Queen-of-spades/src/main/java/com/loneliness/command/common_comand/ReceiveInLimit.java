package com.loneliness.command.common_comand;

import com.loneliness.command.Command;
import com.loneliness.dao.DAO;
import com.loneliness.dao.DAOException;
import com.loneliness.entity.Entity;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

public class ReceiveInLimit<E extends Entity> implements Command<E, Collection<E>, Integer[]> {
    private Logger logger = LogManager.getLogger();
    private final DAO<E> dao;

    public ReceiveInLimit(DAO<E> dao) {
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
    public E undo() throws ServiceException {

        return null;

    }
}
