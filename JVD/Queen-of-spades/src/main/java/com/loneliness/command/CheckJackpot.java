package com.loneliness.command;

import com.loneliness.entity.Profile;
import com.loneliness.service.FactoryServiceCreator;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * класс для проверки достаточности счета с поддержкой транзакций
 *
 * @author Egor Krasouski
 */
public class CheckJackpot implements Command<Object[], Boolean, Object[]> {
    private Object[] objects;
    private Logger logger = LogManager.getLogger();
    private final Service<Profile, Boolean, Object[], Profile> service;

    /**
     * создание нового экземпляра соманды на основе @param
     * @param service объект сервиса который будет выполнять команду
     * @author Egor Krasouski
     *
     */
    @Deprecated
    public CheckJackpot(Service<Profile, Boolean, Object[], Profile> service) {
        this.service = service;
    }

    public CheckJackpot() throws CommandException {
        try {
            this.service = FactoryServiceCreator.getInstance().getCheckJackpotService();
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    /**
     *  @param  note данные по которым происходит проверка  возможности ставки [0] id пользователя [1] сумма данег
     @return данные пользователя
     *
     */
    @Override
    public Boolean execute(Object[] note) throws CommandException {
        try {
            this.objects = note;
            return service.execute(note);
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Object[] undo() {
        return objects;
    }
}
