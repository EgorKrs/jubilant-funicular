package com.loneliness.command;

import com.loneliness.entity.Card;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
/**
 * класс получения колоды карт
 * @author Egor Krasouski
 *
 */
public class ReceiveDeckOfCardsCommand implements Command<Integer,Map<Integer, Card>,Integer> {
    private Integer id;
    private Logger logger = LogManager.getLogger();
    private final Service<Integer, Map<Integer, Card>, Integer, Integer> service;

    public ReceiveDeckOfCardsCommand(Service<Integer, Map<Integer, Card>, Integer, Integer> service) {
        this.service = service;
    }

    @Override
    public Map<Integer, Card> execute(Integer note) throws CommandException {
        this.id=note;
        try {
            return service.execute(note);
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }

    }

    @Override
    public Integer undo() {
        return id;
    }
}
