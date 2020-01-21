package com.loneliness.service.game;

import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;

public class GameHandler {
    private static GameHandler instance;
    private static final ReentrantLock lock=new ReentrantLock();
    private GameHandler(){
        instance=new GameHandler();
    }
    public static GameHandler getInstance(){
        if(instance==null){
            lock.lock();
            if(instance==null){
                instance=new GameHandler();
            }
            lock.unlock();
        }
        return instance;
    }
    private Collection<Game> gameCollection;

}
