package com.loneliess.servise;

public class ServiceFactory {
    private final PointLogic pointLogic=PointLogic.getInstance();
    private final ConeLogic coneLogic=ConeLogic.getInstance();
    private final static ServiceFactory instance=new ServiceFactory();
    private final ServiceValidation serviceValidation=ServiceValidation.getInstance();
    public static ServiceFactory getInstance(){
        return instance;
    }

    public PointLogic getPointLogic() {
        return pointLogic;
    }

    public ConeLogic getConeLogic() {
        return coneLogic;
    }

    public ServiceValidation getServiceValidation() {
        return serviceValidation;
    }
}
