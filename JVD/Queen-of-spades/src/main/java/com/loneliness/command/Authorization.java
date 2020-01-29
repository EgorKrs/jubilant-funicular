package com.loneliness.command;

import com.loneliness.dao.DAOException;
import com.loneliness.dao.sql_dao_impl.UserDAO;

import com.loneliness.entity.User;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Authorization implements Command<User,User,User> {
    private User user;
    private Logger logger = LogManager.getLogger();
    private final UserDAO dao;

    public Authorization(UserDAO dao) {
        this.dao=dao;
    }

    public Authorization() throws ServiceException {
        try {
            this.dao=new UserDAO();
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public User execute(User user) throws ServiceException {
        try {
            this.user=user;
            return dao.receive(this.user);
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public User undo() {
        return  user;
    }
}
