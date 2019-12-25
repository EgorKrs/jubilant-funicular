package com.loneliness.dao;

import java.util.Collection;

public interface  DAO<T> {
    /*
    return :
    1-ok
    2-error
    3-invalid note
    4-db error
    */
    int create(T note);
    int update(T note);
    int delete(T note);
    T receive(T note);
    Collection<T> receiveAll();
    Collection<T> receiveAll(int[] bound);

}
