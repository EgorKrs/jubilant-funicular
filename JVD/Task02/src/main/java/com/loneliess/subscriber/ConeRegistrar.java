package com.loneliess.subscriber;

import com.loneliess.controller.Command;
import com.loneliess.controller.CommandName;
import com.loneliess.controller.CommandProvider;
import com.loneliess.controller.ControllerException;
import com.loneliess.entity.Cone;
import com.loneliess.repository.RepositoryConeRegistrar;
import com.loneliess.repository.RepositoryFactory;
import com.loneliess.servise.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.Flow;

public class ConeRegistrar <T> implements Flow.Subscriber<T>{
    private static final Logger logger= LogManager.getLogger();
    private Flow.Subscription subscription;
    private int id;
    private double surfaceArea;
    private double sideSurfaceArea;
    private double volume;
    private CommandProvider provider=CommandProvider.getCommandProvider();

    public double getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(Cone cone)  {
        try {
            this.surfaceArea = (double) provider.getCommand(CommandName.CALCULATE_SIDE_SURFACE_AREA).execute(cone);
        } catch (ControllerException e) {
            this.surfaceArea=0;
        }
    }

    public double getSideSurfaceArea() {
        return sideSurfaceArea;
    }

    public void setSideSurfaceArea(Cone cone)  {
        try {
            this.sideSurfaceArea = (double)provider.getCommand(CommandName.CALCULATE_SIDE_SURFACE_AREA).execute(cone);
        } catch (ControllerException e) {
            this.sideSurfaceArea=0;
        }
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(Cone cone)  {
        try {
            this.volume = (double)provider.getCommand(CommandName.CALCULATE_VOLUME_OF_CONE).execute(cone);
        } catch (ControllerException e) {
            this.volume=0;
        }
    }

    public Flow.Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Flow.Subscription subscription) {
        this.subscription = subscription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSurfaceArea(double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public void setSideSurfaceArea(double sideSurfaceArea) {
        this.sideSurfaceArea = sideSurfaceArea;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(T item) {
        if(this.id==((Cone)item).getId()) {
            setAll((Cone) item);
            logger.info("ConeRegistrar calculate value for " + item);
        }
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        logger.catching(throwable);
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        logger.info("ConeRegistrar was updated");
    }
    private void setAll(Cone cone){
        this.id=cone.getId();
        setSurfaceArea(cone);
        setSideSurfaceArea(cone);
        setVolume(cone);
    }

}
