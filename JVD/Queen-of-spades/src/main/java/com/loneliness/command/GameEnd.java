package com.loneliness.command;

import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import com.loneliness.service.game.GameData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * класс для завершения игр
 * @author Egor Krasouski
 *
 */
public class GameEnd implements Command<Integer, Boolean, GameData> {
    private Logger logger = LogManager.getLogger();
    private final Service<Integer, Boolean, GameData, GameData> service;
    private GameData gameData;

    public GameEnd(Service<Integer, Boolean, GameData, GameData> service) {
        this.service = service;
    }

    /**
     * @param note данные игры необходимые для подведения итогов игры
     * @return true в случае успеха
     */
    @Override
    public Boolean execute(GameData note) throws CommandException {
        try {
            this.gameData = note;
            service.execute(note);
            return Boolean.TRUE;
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }

    }

    /**
     * @return @return
     * 1-ok
     * -3-невозможно откатить изменения
     */
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