package com.loneliness.service;
/**
 * общий сервисный интерфейс
 * @author Egor Krasouski
 */
public interface Service<E, R, T, P> {
    R execute(T note) throws ServiceException;

    E undo(P note) throws ServiceException;


}
