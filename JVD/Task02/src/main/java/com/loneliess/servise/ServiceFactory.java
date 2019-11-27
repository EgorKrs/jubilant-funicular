package com.loneliess.servise;

public class ServiceFactory {
    private final PointService pointService = PointService.getInstance();
    private final ConeService coneService = ConeService.getInstance();
    private final static ServiceFactory instance=new ServiceFactory();
    private final ServiceValidation serviceValidation=ServiceValidation.getInstance();
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
