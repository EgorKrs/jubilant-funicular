package com.loneliness.command;

import com.loneliness.entity.Profile;
import com.loneliness.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * класс для проверки достаточности счета с поддержкой транзакций
 * @author Egor Krasouski
 *
 */
public class CheckJackpot implements Command<Object[],Boolean,Object[]>{
    private Object[] objects;
    private Logger logger = LogManager.getLogger();
    private final Service<Profile, Boolean, Object[], Profile> service;

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

    @Override
    public Boolean execute(Object[] note) throws CommandException {
        try {
            this.objects =note;
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
