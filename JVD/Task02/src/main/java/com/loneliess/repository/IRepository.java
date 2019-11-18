package com.loneliess.repository;

import java.util.HashMap;

public interface IRepository<Type,Collection> {
    Collection getMap() throws RepositoryException;
    boolean addNode(Type ob) throws RepositoryException;
    boolean save(Collection ob) throws RepositoryException;
    boolean delete(Type ob) throws RepositoryException;
    boolean update(Type ob) throws RepositoryException;
}
