package com.loneliness.service;

import com.loneliness.entity.Entity;
import com.loneliness.service.common_service.*;

import java.util.concurrent.locks.ReentrantLock;

public class FactoryServiceCreator {
    private static final ReentrantLock lock = new ReentrantLock();
    private static FactoryServiceCreator instance;

    public static FactoryServiceCreator getInstance() {
        if (instance == null) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new FactoryServiceCreator();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public <T extends Entity> CreateService<T> getCreateService(Class<T> tClass) throws ServiceException {
        return new CreateService(tClass);
    }

    public <T extends Entity> DeleteService getDeleteService(Class<T> tClass) throws ServiceException {
        return new DeleteService(tClass);
    }

    public <T extends Entity> ReceiveAllService getReceiveAllService(Class<T> tClass) throws ServiceException {
        return new ReceiveAllService(tClass);
    }

    public <T extends Entity> ReceiveByIdService getReceiveByIdService(Class<T> tClass) throws ServiceException {
        return new ReceiveByIdService(tClass);
    }

    public <T extends Entity> ReceiveByUserIdService getReceiveByUserIdService(Class<T> tClass) throws ServiceException {
        return new ReceiveByUserIdService(tClass);
    }

    public <T extends Entity> ReceiveInLimitService getReceiveInLimitService(Class<T> tClass) throws ServiceException {
        return new ReceiveInLimitService(tClass);
    }

    public <T extends Entity> UpdateService getUpdateService(Class<T> tClass) throws ServiceException {
        return new UpdateService(tClass);
    }

    public <T extends Entity> ValidationService getValidationService() throws ServiceException {
        return new ValidationService();
    }

    public <T extends Entity> AddScoreService getAddScoreService() throws ServiceException {
        return new AddScoreService();
    }

    public <T extends Entity> AuthorizationService getAuthorizationService() throws ServiceException {
        return new AuthorizationService();
    }

    public <T extends Entity> GameEndService getGameEndService() throws ServiceException {
        return new GameEndService();
    }

    public <T extends Entity> ReceiveDeckOfCardsService getReceiveDeckOfCardsService() throws ServiceException {
        return new ReceiveDeckOfCardsService();
    }
    public <T extends Entity> CheckJackpotService getCheckJackpotService() throws ServiceException {
        return new CheckJackpotService();
    }
}
