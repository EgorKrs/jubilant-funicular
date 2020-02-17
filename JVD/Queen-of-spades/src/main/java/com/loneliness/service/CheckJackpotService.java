package com.loneliness.service;

import com.loneliness.dao.DAOException;
import com.loneliness.dao.FactoryDAO;
import com.loneliness.dao.sql_dao_impl.ProfileDAO;
import com.loneliness.dao.sql_dao_impl.UserDAO;
import com.loneliness.entity.Profile;
import com.loneliness.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class CheckJackpotService implements Service<Profile, Boolean, Object[], Profile> {
    private final ProfileDAO dao;
    private Logger logger = LogManager.getLogger();

    public CheckJackpotService(ProfileDAO dao) {
        this.dao = dao;
    }

    public CheckJackpotService() throws ServiceException {
        try {
            this.dao = FactoryDAO.getInstance().getProfileDAO();
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Boolean execute(Object[] note) throws ServiceException {
        try {
             Integer id=(Integer) note[0];
             Profile profile=dao.receiveByUserId(id);
            return profile.getScore().compareTo((BigDecimal) note[1]) >= 0;
        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Profile undo(Profile profile) {
        return profile;
    }
}


