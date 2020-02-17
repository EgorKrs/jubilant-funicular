package com.loneliness.dao;
/**
 * общий DAO для тех классов что работают с пользователями
 * @author Egor Krasouski
 */
public interface WorkWithUserDAO<T> {
    T receiveByUserId(Integer id) throws DAOException;
}
