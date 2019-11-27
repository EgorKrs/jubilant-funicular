package com.loneliess.repository;



public class RepositoryFactory {
    private static final RepositoryFactory instance=new RepositoryFactory();
    private final RepositoryCone repositoryCone=new RepositoryCone();
    private final RepositoryConeRegistrar repositoryConeRegistrar=new RepositoryConeRegistrar();
    private RepositoryFactory(){}

    public RepositoryConeRegistrar getRepositoryConeRegistrar() {
        return repositoryConeRegistrar;
    }

    public static RepositoryFactory getInstance() {
        return instance;
    }

    public RepositoryCone getRepositoryCone() {
        return repositoryCone;
    }
}
