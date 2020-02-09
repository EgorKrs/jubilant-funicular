package com.loneliness.dao;

import java.util.Collection;

public interface  DAO<T  > {
    /*
    return :
    1-ok
    -2-error
    -3-invalid note
    -4-db error
    -5 file error
    */
    int create(T note) throws DAOException;
    int update(T note) throws DAOException;
    int delete(T note) throws DAOException;
    int delete(int note) throws DAOException;
    T receive(Integer id) throws DAOException;
    T receive(T note) throws DAOException;
    Collection<T> receiveAll() throws DAOException;
    Collection<T> receiveAll(Integer[] bound) throws DAOException;

}
