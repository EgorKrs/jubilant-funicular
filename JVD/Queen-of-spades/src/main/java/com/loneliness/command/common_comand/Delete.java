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
public class Delete<T> implements Command<Integer, Integer, T> {
    private T data;
    private Logger logger = LogManager.getLogger();
    private final Service<Integer, Integer, T, T> service;

    public Delete(Service<Integer, Integer, T, T> service) {
        this.service = service;
    }

    /**
     * @param data данные для удаления
     * @return 1-ok
     * -2-error
     * -3-invalid note
     * -4-db error
     */
    @Override
    public Integer execute(T data) throws CommandException {
        try {
            this.data = data;
            return service.execute(data);
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    /**
     * @return id новых данных
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
