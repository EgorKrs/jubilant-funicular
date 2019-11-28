package com.loneliness.service;

import com.loneliness.Train;
import com.loneliness.Tunnel;
import com.loneliness.entity.Direction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Dispatcher {
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
    private final Logger logger= LogManager.getLogger();
    private Tunnel tunnel=new Tunnel(3);
    private ReentrantLock locker = new ReentrantLock();
    private Deque<Train> leftQueue=new ArrayDeque<>();
    private Deque<Train> rightQueue=new ArrayDeque<>();
    private Direction lastDirection=Direction.RIGHT;
    public static AtomicInteger successCase=new AtomicInteger();

    private void addTrainToLeftQueue(Train train){
        logger.info("Поезд "+train.getNumber()+" отправлен в Left очередь");
        locker.lock();
        leftQueue.addLast(train);
        locker.unlock();
    }
    private Train getTrainToLeftQueue(){
        Train train;
        locker.lock();
        train=leftQueue.pollFirst();
        locker.unlock();
        return train;
    }
    private void addTrainToRightQueue(Train train){
        logger.info("Поезд "+train.getNumber()+" отправлен в Right очередь");
        locker.lock();
        rightQueue.addLast(train);
        locker.unlock();
    }
    private Train getTrainToRightQueue(){
        Train train;
        locker.lock();
        train=rightQueue.pollFirst();
        locker.unlock();
        return train;
    }
    public void sendToQueue(Train train){
        switch (train.getDirection()){
            case RIGHT:
                addTrainToRightQueue(train);
                break;
            case LEFT:
                addTrainToLeftQueue(train);
                break;
        }
    }
    public void sendToTunnel() {
        service.scheduleWithFixedDelay(() -> {
            Train train;
            if(rightQueue.size()>leftQueue.size()){
                train=getTrainToRightQueue();
            }
            else if(rightQueue.size()<leftQueue.size()){
                train=getTrainToLeftQueue();
            }
            else if(lastDirection.equals(Direction.RIGHT)){
                train=getTrainToRightQueue();
            }
            else {
                train=getTrainToLeftQueue();
            }
            logger.info("Поезд "+train.getNumber() +" отправился в тоннель  c "+train.getDirection()+" стороны" );
            try {
                tunnel.launchTrain(train);
            } catch (ExecutionException | InterruptedException e) {
                logger.catching(e);
                e.printStackTrace();
            }
        },0,1, TimeUnit.SECONDS);

    }

    public Tunnel getTunnel() {
        return tunnel;
    }
}
