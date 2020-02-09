package com.loneliness.service;

import com.loneliness.dao.DAOException;
import com.loneliness.dao.FactoryDAO;
import com.loneliness.dao.sql_dao_impl.CardDAO;
import com.loneliness.entity.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ReceiveDeckOfCardsService implements Service<Integer, Map<Integer, Card>, Integer, Integer> {
    private final CardDAO dao;
    private Logger logger = LogManager.getLogger();

    public ReceiveDeckOfCardsService(CardDAO dao) {
        this.dao = dao;
    }

    public ReceiveDeckOfCardsService() throws ServiceException {
        try {
            this.dao = FactoryDAO.getInstance().getCardDAO();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Map<Integer, Card> execute(Integer note) throws ServiceException {
        try {
            return dao.receiveDeckOfCards(note);
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }

    }

    @Override
    public Integer undo(Integer id) {
        return id;
    }
}
