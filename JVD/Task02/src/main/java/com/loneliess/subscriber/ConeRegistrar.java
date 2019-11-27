package com.loneliess.subscriber;

import com.loneliess.controller.CommandName;
import com.loneliess.controller.CommandProvider;
import com.loneliess.controller.ControllerException;
import com.loneliess.entity.Cone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.Flow;

public class ConeRegistrar <T> implements Flow.Subscriber<T>{
    private Logger logger= LogManager.getLogger();
    private Flow.Subscription subscription;
    private int id;
    private double surfaceArea;
    private double sideSurfaceArea;
    private double volume;

    public double getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(Cone cone)  {
        try {
            this.surfaceArea = (double) CommandProvider.getCommandProvider().getCommand(CommandName.CALCULATE_SIDE_SURFACE_AREA).execute(cone);
        } catch (ControllerException e) {
            this.surfaceArea=0;
        }
    }

    public double getSideSurfaceArea() {
        return sideSurfaceArea;
    }

    public void setSideSurfaceArea(Cone cone)  {
        try {
            this.sideSurfaceArea = (double)CommandProvider.getCommandProvider().getCommand(CommandName.CALCULATE_SIDE_SURFACE_AREA).execute(cone);
        } catch (ControllerException e) {
            this.sideSurfaceArea=0;
        }
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(Cone cone)  {
        try {
            this.volume = (double)CommandProvider.getCommandProvider().getCommand(CommandName.CALCULATE_VOLUME_OF_CONE).execute(cone);
        } catch (ControllerException e) {
            this.volume=0;
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
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
        setAll((Cone)item);
        logger.info("ConeRegistrar calculate value for "+item);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConeRegistrar<?> that = (ConeRegistrar<?>) o;
        return id == that.id &&
                Double.compare(that.surfaceArea, surfaceArea) == 0 &&
                Double.compare(that.sideSurfaceArea, sideSurfaceArea) == 0 &&
                Double.compare(that.volume, volume) == 0 &&
                subscription.equals(that.subscription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscription, id, surfaceArea, sideSurfaceArea, volume);
    }

    @Override
    public String toString() {
        return "ConeRegistrar{" +
                "subscription=" + subscription +
                ", id=" + id +
                ", surfaceArea=" + surfaceArea +
                ", sideSurfaceArea=" + sideSurfaceArea +
                ", volume=" + volume +
                '}';
    }
}
