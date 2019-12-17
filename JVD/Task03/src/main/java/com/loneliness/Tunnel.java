package com.loneliness;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;


public class Tunnel {
    private final Semaphore semaphore;
    private Logger logger = LogManager.getLogger();
    private AtomicInteger quantity = new AtomicInteger();


    public Tunnel(int bandwidth) {
        semaphore = new Semaphore(bandwidth);
        quantity.set(bandwidth);
    }

    public void launchTrain(Train train) {
        try {
            semaphore.acquire();
            quantity.decrementAndGet();
            train.setQuantity(quantity);

            new Thread(train).start();
        } catch (InterruptedException e) {

            logger.catching(e);
        } finally {

            semaphore.release();

        }
    }
    public int getAvailableQuantityOfTrain(){
        return quantity.get();
    }

}
