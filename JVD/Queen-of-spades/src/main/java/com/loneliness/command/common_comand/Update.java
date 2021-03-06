package com.loneliness.command.common_comand;

import com.loneliness.command.Command;
import com.loneliness.command.CommandException;
import com.loneliness.entity.Entity;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * общий класс для обновления данных @see com.loneliness.Entity с поддержкой транзакций
 * @author Egor Krasouski
 *
 */
public class Update<T extends Entity> implements Command<Integer,Integer, T> {
    private T data;
    private final Logger logger = LogManager.getLogger();
    private final Service<Integer, Integer, T, T> service;

    public Update(Service<Integer, Integer, T, T> service) {
        this.service = service;
    }

    public Update setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * @param data данные для обновления
     * @return 1-ok
     * -3-invalid note
     */
    @Override
    public Integer execute(T data) throws CommandException {
        try {
            return service.execute(data);
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    /**
     * return данные до изменения
     */
    @Override
    public Integer undo() throws CommandException {
        try {
            return service.undo(data);
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
