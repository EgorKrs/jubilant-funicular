package com.loneliess.repository;

public interface IRepository<T, C> {
    C getMap() throws RepositoryException;
    boolean addNode(T ob) throws RepositoryException;
    boolean save(C ob) throws RepositoryException;
    boolean delete(T ob) throws RepositoryException;
    boolean update(T ob) throws RepositoryException;
}
