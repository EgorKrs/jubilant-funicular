package com.loneliness.command.common_comand;

import com.loneliness.command.Command;
import com.loneliness.entity.Entity;
import com.loneliness.service.common_service.ValidationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolation;
import java.util.Set;
/**
 * общий класс для валидации данных @see com.loneliness.Entity по ID с поддержкой транзакций
 *
 * @author Egor Krasouski
 */
public class Validation<T extends Entity> implements Command<T, Set<ConstraintViolation<T>>, T> {
    private final Logger logger = LogManager.getLogger();
    private ValidationService<T> service;
    private T data;

    /**
     * @param note данные валидации
     * @return set из ошибок которые были выявлены
     */
    @Override
    public Set<ConstraintViolation<T>> execute(T note) {
        this.data = note;
        return service.execute(note);
    }

    /**
     * @return данные которые проходили вылидацию
     */
    @Override
    public T undo() {
        return data;
    }
}
