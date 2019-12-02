package com.loneliness;

import com.loneliness.service.Dispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;


public class Tunnel {
    private final Semaphore semaphore;
    private Logger logger = LogManager.getLogger();
    private final ReentrantLock locker = new ReentrantLock();
    public AtomicBoolean[] railTracks;
    public AtomicInteger quantity=new AtomicInteger();


    public Tunnel(int bandwidth) {
        semaphore = new Semaphore(bandwidth);
        railTracks = new AtomicBoolean[bandwidth];
        quantity.set( bandwidth);
        for (int i = 0; i < railTracks.length; i++) {
            railTracks[i] = new AtomicBoolean();
        }
    }

    public void launchTrain(Train train,String tunnel) throws ExecutionException, InterruptedException {
        int railTracksNumber = -1;
        try {
            semaphore.acquire();
            locker.lock();
            for (int i = 0; i < railTracks.length; i++)
                if (!railTracks[i].get()) {
                    quantity.decrementAndGet();
                    railTracks[i].set(true);
                    railTracksNumber = i;
                    train.setLaunchArgs(railTracksNumber,quantity,railTracks[i],tunnel);
                    new Thread(train).start();

                    break;
                }

        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.catching(e);
        } finally {

            semaphore.release();
            locker.unlock();
        }
    }

}
