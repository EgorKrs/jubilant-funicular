package com.loneliness.service.user_service;

import com.loneliness.dao.DAOException;
import com.loneliness.dao.sql_dao_impl.UserDAO;
import com.loneliness.entity.Entity;

import com.loneliness.entity.User;
import com.loneliness.service.Command;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckAuthorizationData implements Command<User,User,User> {
    private User user;
    private Logger logger = LogManager.getLogger();
    private final UserDAO dao;

    public CheckAuthorizationData(UserDAO dao) {
        this.dao=dao;
    }

    @Override
    public User execute(User user) throws ServiceException {
        try {
            this.user=user;
            User dbUser=dao.receive(this.user);
            if(dbUser.getLogin().equals(this.user.getLogin()) && dbUser.getPassword().equals(this.user.getPassword())){
                return dbUser;
            }
            else return this.user;

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
