package com.loneliness;

import com.loneliness.entity.Direction;
import com.loneliness.service.Dispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Train implements Runnable {
    private final Logger logger = LogManager.getLogger();
    private Direction direction;
    private final int number;
    private int railTracksNumber;
    private AtomicInteger quantity;
    private AtomicBoolean railTrack;
    private String tunnel;

    public Train(int number, Direction direction) {
        this.number = number;
        this.direction = direction;
    }

    public void setLaunchArgs(int railTracksNumber, AtomicInteger quantity, AtomicBoolean railTrack,String tunnel) {
        this.railTracksNumber = railTracksNumber;
        this.quantity=quantity;
        this.railTrack=railTrack;
        this.tunnel=tunnel;
    }



    public void run() throws TrainException {
            try {
                logger.info("Поезд " + number + " едет по пути  " + (railTracksNumber + 1)+" тонеля "+tunnel);

                TimeUnit.SECONDS.sleep(4);

                logger.info("Поезд " + number + " выехал из пути  " + (railTracksNumber + 1) +" тонеля "+tunnel + " .");
                railTrack.set(false);
                quantity.incrementAndGet();
                Dispatcher.successCase.incrementAndGet();
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.catching(e);
            }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getNumber() {
        return number;
    }

}
