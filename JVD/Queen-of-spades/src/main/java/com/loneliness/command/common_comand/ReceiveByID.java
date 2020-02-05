package com.loneliness.command.common_comand;

import com.loneliness.command.Command;
import com.loneliness.command.CommandException;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReceiveByID<T> implements Command<T,T,T> {
    private T data;
    private Logger logger = LogManager.getLogger();
    private final Service<T, T, T, T> service;

    public ReceiveByID(Service<T, T, T, T> service) {
        this.service = service;
    }

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

    @Override
    public T undo() {
        return data;
    }
}