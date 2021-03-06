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
    private static final ReentrantLock locker = new ReentrantLock();
    private Dispatcher(){ }
    private static  Dispatcher instance;

    public static Dispatcher getInstance() {
        if(instance==null){
            locker.lock();
            instance=new Dispatcher();
            locker.unlock();
        }
        return instance;
    }


    private ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
    private final Logger logger= LogManager.getLogger();
    private Tunnel tunnel=new Tunnel(5);
    private Tunnel tunnel0=new Tunnel(3);
    private Deque<Train> leftQueue=new ArrayDeque<>();
    private Deque<Train> rightQueue=new ArrayDeque<>();
    private Direction lastDirection;
    public static AtomicInteger successCase=new AtomicInteger();
    private int maxQuantityOfTrainToOneDirection=4;
    private AtomicInteger toOneDirection=new AtomicInteger(0);
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

    private int getSizeOfRightQueue(){
        int size;
        locker.lock();
        size=rightQueue.size();
        locker.unlock();
        return size;
    }
    private int getSizeOfLeftQueue(){
        int size;
        locker.lock();
        size=leftQueue.size();
        locker.unlock();
        return size;
    }

    public void sendToQueue(Train train){
        if(lastDirection==null){
            lastDirection=train.getDirection();
        }
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
            if(toOneDirection.get()==maxQuantityOfTrainToOneDirection){
                if(lastDirection.equals(Direction.LEFT)){
                    lastDirection=Direction.RIGHT;
                }
                else if(lastDirection.equals(Direction.RIGHT)){
                    lastDirection=Direction.LEFT;
                }
                toOneDirection.set(0);
            }
            else if(getSizeOfLeftQueue()==0){
                lastDirection=Direction.RIGHT;
                logger.info("left empty");
                toOneDirection.set(0);
            }
            else if(getSizeOfRightQueue()==0){
                lastDirection=Direction.LEFT;
                logger.info("right empty");
                toOneDirection.set(0);
            }



            if(lastDirection.equals(Direction.RIGHT)){
                train=getTrainToRightQueue();
                toOneDirection.incrementAndGet();
            }
            else {
                train=getTrainToLeftQueue();
                toOneDirection.incrementAndGet();
            }
            logger.info(toOneDirection.get());


            try {
                    while (tunnel.getAvailableQuantityOfTrain()==0&&tunnel0.getAvailableQuantityOfTrain()==0){
                        TimeUnit.SECONDS.sleep(1);
                        logger.info("ожидание");
                    }
                    if (tunnel.getAvailableQuantityOfTrain() > tunnel0.getAvailableQuantityOfTrain()) {

                        logger.info("Поезд " + train.getNumber() + " отправился в 2 тонель  c " + train.getDirection() + " стороны");
                        tunnel.launchTrain(train);
                    } else {
                        logger.info("Поезд " + train.getNumber() + " отправился в 1 тонель  c " + train.getDirection() + " стороны");
                        tunnel0.launchTrain(train);
                    }


            } catch (InterruptedException e) {
                logger.catching(e);
            }
        },0,60, TimeUnit.MILLISECONDS);

    }
}
