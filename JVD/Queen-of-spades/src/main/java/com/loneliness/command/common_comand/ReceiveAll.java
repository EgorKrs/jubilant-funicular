package com.loneliness.command.common_comand;

import com.loneliness.command.Command;
import com.loneliness.command.CommandException;
import com.loneliness.entity.Entity;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * общий класс для получения всех данных @see com.loneliness.Entity  с поддержкой транзакций
 * @author Egor Krasouski
 *
 */
public class ReceiveAll<T extends Entity> implements Command<Collection<T>, Collection<T>, T> {
    private Logger logger = LogManager.getLogger();
    private final Service<Collection<T>, Collection<T>, T, T> service;

    public ReceiveAll(Service<Collection<T>, Collection<T>, T, T> service) {
        this.service = service;
    }

    /**
     * @param data тип данных для получения
     * @return коллекция из полученных данных
     */
    @Override
    public Collection<T> execute(T data) throws CommandException {
        try {
            return service.execute(data);
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    /**
     * @return новая пустая коллекция
     */
    @Override
    public Collection<T> undo() {
        return new ConcurrentLinkedQueue<>();
    }
}
