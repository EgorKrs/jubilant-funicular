package com.loneliness.dao;

import com.loneliness.dao.sql_dao_impl.*;

import java.util.concurrent.locks.ReentrantLock;

public class FactoryDAO {
    private static final ReentrantLock lock = new ReentrantLock();
    private static FactoryDAO instance;
    private final AccountDAO accountDAO;
    private final CardDAO cardDAO;
    private final MessageDAO messageDAO;
    private final NewsDAO newsDAO;
    private final PictureDAO pictureDAO;
    private final PicturesInNewsDAO picturesInNewsDAO;
    private final ProfileDAO profileDAO;
    private final UserDAO userDAO;

    private FactoryDAO() throws DAOException {
        accountDAO = new AccountDAO();
        cardDAO = new CardDAO();
        messageDAO = new MessageDAO();
        newsDAO = new NewsDAO();
        pictureDAO = new PictureDAO();
        picturesInNewsDAO = new PicturesInNewsDAO();
        profileDAO = new ProfileDAO();
        userDAO = new UserDAO();
    }

    public static FactoryDAO getInstance() throws DAOException {
        try {
            if (instance == null) {
                lock.lock();
                if (instance == null) {
                    instance = new FactoryDAO();
                }
            }
            return instance;
        } finally {
            lock.unlock();
        }
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public CardDAO getCardDAO() {
        return cardDAO;
    }

    public MessageDAO getMessageDAO() {
        return messageDAO;
    }

    public NewsDAO getNewsDAO() {
        return newsDAO;
    }

    public PictureDAO getPictureDAO() {
        return pictureDAO;
    }

    public PicturesInNewsDAO getPicturesInNewsDAO() {
        return picturesInNewsDAO;
    }

    public ProfileDAO getProfileDAO() {
        return profileDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }
}
