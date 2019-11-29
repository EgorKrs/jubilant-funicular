package com.loneliess.servise;

import com.loneliess.repository.RepositoryFactory;

public class ServiceFactory {
    private final PointService pointService = new PointService();
    private final ConeService coneService ;
    private final static ServiceFactory instance=new ServiceFactory();
    private final ServiceValidation serviceValidation=new ServiceValidation();
    public static ServiceFactory getInstance(){
        return instance;
    }

    private ServiceFactory(){
        coneService = new ConeService(RepositoryFactory.getInstance().getRepositoryCone(),serviceValidation,new PointService());
    }
    public PointService getPointService() {
        return pointService;
    }

    public ConeService getConeService() {
        return coneService;
    }

    public ServiceValidation getServiceValidation() {
        return serviceValidation;
    }
}
