package com.loneliness.service;

import com.loneliness.dao.DAOException;
import com.loneliness.entity.Entity;

public interface Command<E,R ,T > {
    R execute(T note) throws ServiceException;
    E undo() throws ServiceException;
}
