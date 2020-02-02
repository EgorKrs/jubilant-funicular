package com.loneliness.service.game;

import com.loneliness.entity.Card;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class GameHandler {
    private Logger logger = LogManager.getLogger();
    private final AtomicInteger idGen = new AtomicInteger(0);
    private static GameHandler instance;
    private static final ReentrantLock lock=new ReentrantLock();
    private Map<Integer, Game> gameCollection = new ConcurrentHashMap<Integer, Game>();
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


    public Set[] playGame(int id, BigDecimal jackpot, Card card) throws ServiceException {
        Game game = new Game(idGen.incrementAndGet(), 1, id, jackpot, card);
        gameCollection.put(game.getGameID(), game);
        return game.playGame();
    }

    public boolean finishGAme(int gameID) {
        boolean result = gameCollection.get(gameID).finishGame();
        gameCollection.remove(gameID);
        return result;
    }

    public Game getGameById(int id) {
        return gameCollection.get(id);
    }


}
