package com.loneliness.command.common_comand;

import com.loneliness.command.Command;
import com.loneliness.command.CommandException;
import com.loneliness.entity.Entity;
import com.loneliness.service.Service;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Create<T extends Entity> implements Command<Integer,Integer,T> {
    private int id;
    private Logger logger = LogManager.getLogger();
    private final Service<Integer, Integer, T, Integer> service;

    public Create(Service<Integer, Integer, T, Integer> service) {
        this.service = service;
    }

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
