package com.loneliness;

import com.loneliness.entity.Direction;
import com.loneliness.service.Dispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Train implements Runnable {
    private final Logger logger= LogManager.getLogger();
    private Direction direction;
    private final int number;
    private Semaphore semaphore;
    private ReentrantLock locker;
    private boolean[] railTracks;

    public Train(int number,Direction direction){
        this.number=number;
        this.direction=direction;
    }
    public Train(int number,Direction direction,Semaphore semaphore,ReentrantLock locker,boolean[] railTracks){
        this.number=number;
        this.direction=direction;
        this.semaphore=semaphore;
        this.locker=locker;
        this.railTracks=railTracks;
    }
    public void setLaunchArgs(Semaphore semaphore,ReentrantLock locker,boolean[] railTracks){
        this.semaphore=semaphore;
        this.locker=locker;
        this.railTracks=railTracks;
    }
    private boolean isCheckLaunchArgs(){
        return semaphore!=null&&locker!=null&&railTracks.length!=0;
    }

    public void run() throws TrainException{
        boolean complete=false;
        if(isCheckLaunchArgs()) {
            int railTracksNumber = -1;
            try {
                semaphore.acquire();
                locker.lock();
                for (int i = 0; i < railTracks.length; i++)
                    if (!railTracks[i]) {
                        railTracks[i] = true;
                        railTracksNumber = i;
                        logger.info("Поезд " + number + " едет по пути  " + (railTracksNumber+1));
                        break;
                    }
                locker.unlock();
                TimeUnit.SECONDS.sleep(2);
                locker.lock();
                railTracks[railTracksNumber] = false;
                logger.info("Поезд " + number + " выехал из пути  " + (railTracksNumber+1) + " .");
                Dispatcher.successCase.incrementAndGet();
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.catching(e);
            } finally {
                semaphore.release();
                locker.unlock();
            }

        }
        else {
            throw new TrainException("Не заданы аргументы для запуска");
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

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public ReentrantLock getLocker() {
        return locker;
    }

    public void setLocker(ReentrantLock locker) {
        this.locker = locker;
    }

    public boolean[] getRailTracks() {
        return railTracks;
    }

    public void setRailTracks(boolean[] railTracks) {
        this.railTracks = railTracks;
    }
}
