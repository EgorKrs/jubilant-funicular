package com.loneliess.repository;

import com.loneliess.entity.Cone;

import java.util.Collection;

public interface IRepository<T> {
    boolean add(T ob) throws RepositoryException;
    boolean addAll(Collection<T> ob) throws RepositoryException;
    boolean delete(T ob) throws RepositoryException;
    boolean update(T ob) throws RepositoryException;

}
