package com.loneliness.command.common_comand;

import com.loneliness.command.Command;
import com.loneliness.command.CommandException;
import com.loneliness.entity.Entity;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
/**
 * общий класс для получения данных @see com.loneliness.Entity в числовом диапазоне с поддержкой транзакций
 * @author Egor Krasouski
 *
 */
public class ReceiveInLimit<E extends Entity> implements Command<E, Collection<E>, Integer[]> {
    private Logger logger = LogManager.getLogger();
    private final Service<E, Collection<E>, Integer[], E> service;

    public ReceiveInLimit(Service<E, Collection<E>, Integer[], E> service) {
        this.service = service;
    }

    @Override
    public Collection<E> execute(Integer[] data) throws CommandException {
        try {
            return service.execute(data);
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public E undo() throws CommandException {
        return null;
    }
}
