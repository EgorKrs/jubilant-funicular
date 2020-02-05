package com.loneliness.service;

import com.loneliness.dao.DAOException;
import com.loneliness.dao.sql_dao_impl.UserDAO;
import com.loneliness.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthorizationService implements Service<User, User, User, User> {
    private final UserDAO dao;
    private Logger logger = LogManager.getLogger();

    public AuthorizationService(UserDAO dao) {
        this.dao = dao;
    }

    public AuthorizationService() throws ServiceException {
        try {
            this.dao = new UserDAO();
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public User execute(User user) throws ServiceException {
        try {
            return dao.receive(user);
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public User undo(User user) {
        return user;
    }
}
