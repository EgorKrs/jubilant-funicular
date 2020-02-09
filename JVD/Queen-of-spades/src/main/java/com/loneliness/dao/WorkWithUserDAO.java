package com.loneliness.dao;

public interface WorkWithUserDAO<T> {
    T receiveByUserId(Integer id) throws DAOException;
}
