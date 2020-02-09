package com.loneliness.command;

import com.loneliness.dao.DAOException;
import com.loneliness.dao.sql_dao_impl.CardDAO;
import com.loneliness.entity.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ReceiveDeckOfCardsCommand implements Command<Integer,Map<Integer, Card>,Integer> {
    private Integer id;
    private Logger logger = LogManager.getLogger();
    private final CardDAO dao;

    public ReceiveDeckOfCardsCommand(CardDAO dao) {
        this.dao=dao;
    }

    @Override
    public Map<Integer, Card> execute(Integer note) throws CommandException {
        this.id=note;
        try {
            return dao.receiveDeckOfCards(note);
        } catch (DAOException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }

    }

    @Override
    public Integer undo() {
        return id;
    }
}
