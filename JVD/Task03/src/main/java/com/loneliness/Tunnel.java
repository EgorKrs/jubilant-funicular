package com.loneliness;

import com.loneliness.service.Dispatcher;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Tunnel {
    private final Semaphore semaphore;
    private final ReentrantLock locker = new ReentrantLock();
    private final boolean[] railTracks;

    public Tunnel(int bandwidth) {
        semaphore = new Semaphore(bandwidth);
        railTracks = new boolean[bandwidth];
    }

    public void launchTrain(Train train) throws ExecutionException, InterruptedException {
        train.setLaunchArgs(semaphore, locker, railTracks);
        new Thread(train).start();
    }
}
