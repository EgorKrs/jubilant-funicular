package com.loneliness.command.common_comand;

import com.loneliness.command.Command;
import com.loneliness.command.CommandException;
import com.loneliness.entity.Entity;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * общий класс для получения данных @see com.loneliness.Entity по ID пользователя с поддержкой транзакций
 * @author Egor Krasouski
 *
 */
public class ReceiveByUserId<T extends Entity> implements Command<T, T, T> {
    private final Service<T, T, T, T> service;
    private T data;
    private Logger logger = LogManager.getLogger();

    public ReceiveByUserId(Service<T, T, T, T> service) {
        this.service = service;
    }

    /**
     * @param data тип данных для получения
     * @return данные
     */
    @Override
    public T execute(T data) throws CommandException {
        try {
            this.data = data;
            return service.execute(data);
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    /**
     * @return данные по которым был осуществлен поиск
     */
    @Override
    public T undo() {
        return data;
    }
}
