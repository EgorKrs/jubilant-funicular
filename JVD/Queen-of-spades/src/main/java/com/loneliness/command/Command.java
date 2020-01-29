package com.loneliness.command;

import com.loneliness.dao.DAOException;
import com.loneliness.entity.Entity;
import com.loneliness.service.ServiceException;

public interface Command<E,R ,T > {
    R execute(T note) throws ServiceException;
    E undo() throws ServiceException;
}
