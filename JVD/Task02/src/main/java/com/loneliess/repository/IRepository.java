package com.loneliess.repository;

import java.util.HashMap;

public interface IRepository<Type,Collection> {
    Collection getMap() throws RepositoryException;
    boolean AddNode(Type ob) throws RepositoryException;
    boolean Save(Collection ob) throws RepositoryException;
}
