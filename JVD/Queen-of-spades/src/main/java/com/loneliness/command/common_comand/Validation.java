package com.loneliness.command.common_comand;

import com.loneliness.command.Command;
import com.loneliness.entity.Entity;
import com.loneliness.service.common_service.ValidationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class Validation<T extends Entity> implements Command<T, Set<ConstraintViolation<T>>,T> {
    private final Logger logger = LogManager.getLogger();
    private ValidationService<T> service;
    private T data;
    @Override
    public Set<ConstraintViolation<T>> execute(T note) {
        this.data=note;
        return service.execute(note);
    }

    @Override
    public T undo() {
        return data;
    }
}
