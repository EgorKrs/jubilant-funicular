package com.loneliness.service;

import com.loneliness.dao.DAOException;
import com.loneliness.dao.FactoryDAO;
import com.loneliness.dao.sql_dao_impl.UserDAO;
import com.loneliness.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class AuthorizationService implements Service<User, User, User, User> {
    private final UserDAO dao;
    private Logger logger = LogManager.getLogger();
    private PasswordService passwordService;


    public AuthorizationService(UserDAO dao) {
        this.dao = dao;
        passwordService = new PasswordService();
    }

    public AuthorizationService() throws ServiceException {
        try {
            this.dao = FactoryDAO.getInstance().getUserDAO();
            passwordService = new PasswordService();
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public User execute(User user) throws ServiceException {
        try {

            User dbUser = dao.receive(user);
            if (passwordService.validatePassword(user.getPassword(), dbUser.getPassword())) {
                return dbUser;
            }
            return user;
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public User undo(User user) {
        return user;
    }


}
