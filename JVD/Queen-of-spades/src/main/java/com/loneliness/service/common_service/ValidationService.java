package com.loneliness.service.common_service;

import com.loneliness.entity.Entity;
import com.loneliness.service.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class ValidationService<T extends Entity> implements Service<T, Set<ConstraintViolation<T>>, T, T> {
    private Validator validator;

    public ValidationService() {
        validator = javax.validation.Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public Set<ConstraintViolation<T>> execute(T note) {
        return validator.validate(note);
    }

    @Override
    public T undo(T note) {
        return null;
    }
}
