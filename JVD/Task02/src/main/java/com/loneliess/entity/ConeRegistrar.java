package com.loneliess.entity;

import com.loneliess.controller.CommandName;
import com.loneliess.controller.CommandProvider;
import com.loneliess.controller.ControllerException;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Flow;

public class ConeRegistrar <T> implements Flow.Subscriber<T>{
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

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(T item) {
        setAll((Cone)item);
        System.out.println("Got : " + item);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Done");
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
