package com.loneliness.service.game;

import com.loneliness.command.CommandException;
import com.loneliness.entity.Card;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class GameHandler {
    private Logger logger = LogManager.getLogger();
    private static final GameHandler instance = new GameHandler();

    private static final ReentrantLock lock=new ReentrantLock();
    private Map<String, Game> gameCollection = new ConcurrentHashMap<>();


    public static GameHandler getInstance(){
        return instance;
    }


    public Set[] playGame(String gameId, int userId, BigDecimal jackpot, Card card) throws CommandException, ServiceException {
        Game game = new Game(gameId, 1, userId, jackpot, card);
        gameCollection.put(game.getGameId(), game);
        return game.playGame();
    }

    public Game playGame(String gameId, int userId, String jackpot, Card card) throws ServiceException, CommandException {
        Game game = new Game(gameId, 0, userId, new BigDecimal(jackpot), card);
        gameCollection.put(game.getGameId(), game);
        game.playGame();
        return game;
    }

    public boolean finishGame(String gameID) {
        boolean result = gameCollection.get(gameID).finishGame();
        gameCollection.remove(gameID);
        return result;
    }

    public Game getGameByGamerID(int id) {
        for (Game game :
                gameCollection.values()) {
            if (game.getGamerId() == id)
                return game;

        }
        return null;
    }

    public Game getGame(String id) {
        return gameCollection.get(id);
    }


}
