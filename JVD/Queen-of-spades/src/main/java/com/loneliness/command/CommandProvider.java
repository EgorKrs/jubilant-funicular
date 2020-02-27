package com.loneliness.command;

import com.loneliness.command.common_comand.*;
import com.loneliness.entity.Card;
import com.loneliness.entity.Entity;
import com.loneliness.entity.User;
import com.loneliness.service.FactoryServiceCreator;
import com.loneliness.service.ServiceException;
import com.loneliness.service.game.GameData;

import javax.validation.ConstraintViolation;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
/**
 * класс для получения комманд
 * @author Egor Krasouski
 *
 */
public class CommandProvider {
    private static final CommandProvider instance = new CommandProvider();
    private FactoryServiceCreator creator = FactoryServiceCreator.getInstance();

    public static CommandProvider getInstance() {
        return instance;
    }

    public <T extends Entity> Command<Integer, Integer, T> create(Class<T> tClass) throws CommandException {
        try {
            return new <T>Create(creator.getCreateService(tClass));
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    public <T extends Entity> Command<Integer, Integer, T> delete(Class<T> tClass) throws CommandException {
        try {
            return new <T>Delete(creator.getDeleteService(tClass));
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    public <T extends Entity> Command<Integer, Integer, T> receiveAll(Class<T> tClass) throws CommandException {
        try {
            return new <T>ReceiveAll(creator.getReceiveAllService(tClass));
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    public <T extends Entity> Command<T, T, T> receiveByID(Class<T> tClass) throws CommandException {
        try {
            return new <T>ReceiveByID(creator.getReceiveByIdService(tClass));
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    public <T extends Entity> Command<T, T, T> receiveByUserId(Class<T> tClass) throws CommandException {
        try {
            return new <T>ReceiveByUserId(creator.getReceiveByUserIdService(tClass));
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    public <T extends Entity> Command<T, Collection<T>, Integer[]> receiveInLimit(Class<T> tClass) throws CommandException {
        try {
            return new <T>ReceiveInLimit(creator.getReceiveInLimitService(tClass));
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    public <T extends Entity> Command<Integer, Integer, T> update(Class<T> tClass) throws CommandException {
        try {
            return new <T>Update(creator.getUpdateService(tClass));
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    public <T extends Entity> Command<T, Set<ConstraintViolation<T>>, T> validation(Class<T> tClass) throws CommandException {
        return new <T>Validation();
    }

    public Command<Integer, Integer, Object[]> addScoreCommand() throws CommandException {
        try {
            return new AddScoreCommand(creator.getAddScoreService());
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    public <T extends Entity> Command<User, User, User> authorization() throws CommandException {
        try {
            return new <T>Authorization(creator.getAuthorizationService());
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    public <T extends Entity> Command<Integer, Boolean, GameData> gameEnd() throws CommandException {
        try {
            return new <T>GameEnd(creator.getGameEndService());
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    public <T extends Entity> Command<Integer, Map<Integer, Card>, Integer> receiveDeckOfCardsCommand() throws CommandException {
        try {
            return new <T>ReceiveDeckOfCardsCommand(creator.getReceiveDeckOfCardsService());
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
    public Command<Object[],Boolean,Object[]> checkJackpot() throws CommandException {
        try {
            return new CheckJackpot(creator.getCheckJackpotService());
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
