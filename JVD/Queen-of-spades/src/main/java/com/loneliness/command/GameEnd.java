package com.loneliness.command;

import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import com.loneliness.service.game.GameData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameEnd implements Command<Integer, Boolean, GameData> {
    private Logger logger = LogManager.getLogger();
    private final Service<Integer, Boolean, GameData, GameData> service;
    private GameData gameData;

    public GameEnd(Service<Integer, Boolean, GameData, GameData> service) {
        this.service = service;
    }

    @Override
    public Boolean execute(GameData note) throws CommandException {
        try {
            this.gameData = note;
            service.execute(note);

        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }
        return null;
    }

    @Override
    public Integer undo() throws CommandException {
        try {
            return service.undo(gameData);
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }


}