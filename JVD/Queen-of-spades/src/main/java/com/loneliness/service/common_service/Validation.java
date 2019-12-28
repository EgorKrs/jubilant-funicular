package com.loneliness.service.common_service;

import com.loneliness.entity.Entity;
import com.loneliness.service.Command;
import com.loneliness.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class Validation<T extends Entity> implements Command<T, Set<ConstraintViolation<T>>,T> {
    private final Logger logger = LogManager.getLogger();
    private Validator validator;
    private T data;
    public Validation(){
        validator = javax.validation.Validation.buildDefaultValidatorFactory().getValidator();
    }
    @Override
    public Set<ConstraintViolation<T>> execute(T note) {
        this.data=note;
        return validator.validate(note);
    }

    @Override
    public T undo() {
        return data;
    }
}
