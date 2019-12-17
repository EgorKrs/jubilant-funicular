package com.loneliness;

import com.loneliness.entity.Direction;
import com.loneliness.service.Dispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Train implements Runnable {
    private final Logger logger = LogManager.getLogger();
    private Direction direction;
    private final int number;
    private AtomicInteger quantity;

    public AtomicInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(AtomicInteger quantity) {
        this.quantity = quantity;
    }

    public Train(int number, Direction direction) {
        this.number = number;
        this.direction = direction;
    }

    public void run() throws TrainException {
            try {
                logger.info("Поезд " + number + " едет по тонелю ");
                TimeUnit.SECONDS.sleep(4);

                logger.info("Поезд " + number + " выехал из тонеля.");
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
