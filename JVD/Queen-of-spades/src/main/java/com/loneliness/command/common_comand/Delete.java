package com.loneliness.command.common_comand;

import com.loneliness.command.Command;
import com.loneliness.command.CommandException;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * общий класс для удаление данных @see com.loneliness.Entity с поддержкой транзакций
 * @author Egor Krasouski
 *
 */
public class Delete<T> implements Command<Integer,Integer,  T> {
    private T data;
    private Logger logger = LogManager.getLogger();
    private final Service<Integer, Integer, T, T> service;

    public Delete(Service<Integer, Integer, T, T> service) {
        this.service = service;
    }

    @Override
    public Integer execute(T data) throws CommandException {
        try {
            this.data =data;
            return service.execute(data);
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

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
