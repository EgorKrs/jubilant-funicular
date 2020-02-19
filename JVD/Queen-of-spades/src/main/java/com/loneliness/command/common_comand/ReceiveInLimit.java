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
 *
 * @author Egor Krasouski
 */
public class ReceiveInLimit<E extends Entity> implements Command<Integer[], Collection<E>, Integer[]> {
    private Logger logger = LogManager.getLogger();
    private final Service<E, Collection<E>, Integer[], E> service;
    private Integer[] data;

    public ReceiveInLimit(Service<E, Collection<E>, Integer[], E> service) {
        this.service = service;
    }

    /**
     * @param data массив из 2 элементов 1 начало 2 конец интервала для получения
     * @return данные
     */
    @Override
    public Collection<E> execute(Integer[] data) throws CommandException {
        try {
            return service.execute(data);
        } catch (ServiceException e) {
            logger.catching(e);
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    /**
     * @return интервал в котором был осуществлен поиск
     */
    @Override
    public Integer[] undo() throws CommandException {
        return data;
    }
}
