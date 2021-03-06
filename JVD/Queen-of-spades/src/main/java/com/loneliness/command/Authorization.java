package com.loneliness.command;

import com.loneliness.entity.User;
import com.loneliness.service.AuthorizationService;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * класс для авторизации с поддержкой транзакций
 *
 * @author Egor Krasouski
 */
public class Authorization implements Command<User, User, User> {
    private User user;
    private Logger logger = LogManager.getLogger();
    private final Service<User, User, User, User> service;

    /**
     * создание нового экземпляра соманды на основе @param
     *
     * @param service объект сервиса который будет выполнять команду
     * @author Egor Krasouski
     */
    @Deprecated
    public Authorization(Service<User, User, User, User> service) {
        this.service = service;
    }

    public Authorization() throws CommandException {
        try {
            this.service = new AuthorizationService();
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    /**
     * @param user данные пользователя необходимые для регистрации
     * @return данные пользователя
     */
    @Override
    public User execute(User user) throws CommandException {
        try {
            this.user = user;
            return service.execute(this.user);
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    /**
     * @return user данные пользователя необходимые для регистрации
     */
    @Override
    public User undo() {
        return user;
    }
}
