package com.loneliess.servise;

public class ServiceFactory {
    private final PointService pointService = new PointService();
    private final ConeService coneService = new ConeService();
    private final static ServiceFactory instance=new ServiceFactory();
    private final ServiceValidation serviceValidation=new ServiceValidation();
    public static ServiceFactory getInstance(){
        return instance;
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
