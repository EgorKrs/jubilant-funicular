package com.loneliess.repository;


import com.loneliess.ConeParser;
import com.loneliess.resource_provider.PathManager;
import com.loneliess.servise.ServiceFactory;

public class RepositoryFactory {
    private static final RepositoryFactory instance=new RepositoryFactory();
    private final RepositoryCone repositoryCone;
    private final RepositoryConeRegistrar repositoryConeRegistrar=new RepositoryConeRegistrar();
    private RepositoryFactory(){
        repositoryCone=new RepositoryCone(new DataAccess(),new ConeParser(),
                PathManager.getConeDataFile(), ServiceFactory.getInstance().getConeService());

    }

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
