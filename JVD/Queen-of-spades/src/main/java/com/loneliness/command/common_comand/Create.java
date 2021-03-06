package com.loneliness.command.common_comand;

import com.loneliness.command.Command;
import com.loneliness.command.CommandException;
import com.loneliness.entity.Entity;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * общий класс для создания данных @see com.loneliness.Entity с поддержкой транзакций
 * @author Egor Krasouski

 *
 */
public class Create<T extends Entity> implements Command<Integer,Integer,T> {
    private int id;
    private Logger logger = LogManager.getLogger();
    private final Service<Integer, Integer, T, Integer> service;

    public Create(Service<Integer, Integer, T, Integer> service) {
        this.service = service;
    }
/**
 * @param data данные для создания
 * @return id созданных данных
 */
    @Override
    public Integer execute(T data) throws CommandException {
        int id;
        try {
            id = service.execute(data);
            this.id = id;
            return id;
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }

    }

    /**
     * @return 1-ok
     * -2-error
     * -3-invalid note
     * -4-db error
     */
    @Override
    public Integer undo() throws CommandException {
        try {
            return service.undo(id);
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
