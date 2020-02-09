package com.loneliness.service;

import com.loneliness.dao.DAOException;
import com.loneliness.dao.FactoryDAO;
import com.loneliness.dao.sql_dao_impl.AccountDAO;
import com.loneliness.dao.sql_dao_impl.ProfileDAO;
import com.loneliness.entity.Account;
import com.loneliness.entity.Profile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class AddScoreService implements Service<Integer, Integer, Object[], Object[]> {
    private final ProfileDAO profileDAO;
    private final AccountDAO accountDAO;
    private Object[] data;
    private Logger logger = LogManager.getLogger();

    public AddScoreService(ProfileDAO profileDAO, AccountDAO accountDAO) {
        this.profileDAO = profileDAO;
        this.accountDAO = accountDAO;
    }

    public AddScoreService() throws ServiceException {
        try {
            FactoryDAO factoryDAO = FactoryDAO.getInstance();
            this.profileDAO = factoryDAO.getProfileDAO();
            this.accountDAO = factoryDAO.getAccountDAO();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }

    }

    @Override
    public Integer execute(Object[] note) throws ServiceException {
        try {
            int user_id = (Integer) note[0];
            BigDecimal sumOfMoney = (BigDecimal) note[1];
            this.data = note;
            Account account = accountDAO.getByUserId(user_id);
            Profile profile = profileDAO.getProfileByUserId(user_id);
            if (account.getSumOfMoney().compareTo(sumOfMoney) > 0) {
                if (accountDAO.updateSunOfMoney(user_id, account.getSumOfMoney().
                        add(sumOfMoney.multiply(new BigDecimal("-1")))) == 1) {
                    if (profileDAO.updateScore(user_id, profile.getScore().add(sumOfMoney)) == 1) {
                        return 1;
                    } else {
                        accountDAO.updateSunOfMoney(user_id, account.getSumOfMoney().add(sumOfMoney));
                        return -1;
                    }
                } else {
                    return -2;
                }
            } else {
                return -3;
            }

        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Integer undo(Object[] note) throws ServiceException {
        int user_id = (Integer) note[0];
        BigDecimal sumOfMoney = (BigDecimal) note[1];
        this.data = note;
        try {
            Account account = accountDAO.getByUserId(user_id);
            Profile profile = profileDAO.getProfileByUserId(user_id);
            if (account.getSumOfMoney().compareTo(sumOfMoney) > 0) {
                if (accountDAO.updateSunOfMoney(user_id, account.getSumOfMoney().
                        add(sumOfMoney)) == 1) {
                    if (profileDAO.updateScore(user_id, profile.getScore().add(sumOfMoney)) == 1) {
                        return 1;
                    } else {
                        accountDAO.updateSunOfMoney(user_id, account.getSumOfMoney().add(sumOfMoney.
                                multiply(new BigDecimal("-1"))));
                        return -1;
                    }
                } else {
                    return -2;
                }
            } else {
                return -3;
            }

        } catch (DAOException e) {
            logger.catching(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }
}
