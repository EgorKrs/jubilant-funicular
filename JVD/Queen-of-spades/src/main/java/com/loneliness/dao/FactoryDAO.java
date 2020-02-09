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

            if (instance == null) {
                try {
                    lock.lock();
                    if (instance == null) {
                        instance = new FactoryDAO();
                    }
                } finally {
                    lock.unlock();
                }
            }
        return instance;

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

    public <T> DAO<T> getDao(Class<T> tClass) throws DAOException {
        try {
            switch (tClass.getName()) {
                case "com.loneliness.entity.Account":
                    return (DAO<T>) getAccountDAO();
                case "com.loneliness.entity.Card":
                    return (DAO<T>) getCardDAO();
                case "com.loneliness.entity.Message":
                    return (DAO<T>) getMessageDAO();
                case "com.loneliness.entity.News":
                    return (DAO<T>) getNewsDAO();
                case "com.loneliness.entity.Picture":
                    return (DAO<T>) getPictureDAO();
                case "com.loneliness.entity.PicturesInNews ":
                    return (DAO<T>) getPicturesInNewsDAO();
                case "com.loneliness.entity.Profile":
                    return (DAO<T>) getProfileDAO();
                case "com.loneliness.entity.User":
                    return (DAO<T>) getUserDAO();
            }
        } catch (ClassCastException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return null;
    }

    public <T> DAO<T> getDao(String ob) throws DAOException {
        try {
            switch (ob) {
                case "Account":
                    return (DAO<T>) getAccountDAO();
                case "Card":
                    return (DAO<T>) getCardDAO();
                case "Message":
                    return (DAO<T>) getMessageDAO();
                case "News":
                    return (DAO<T>) getNewsDAO();
                case "Picture":
                    return (DAO<T>) getPictureDAO();
                case "PicturesInNews":
                    return (DAO<T>) getPicturesInNewsDAO();
                case "Profile":
                    return (DAO<T>) getProfileDAO();
                case "User":
                    return (DAO<T>) getUserDAO();
            }
        } catch (ClassCastException e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }
        return null;
    }
}
