package com.loneliess.servise;

import com.loneliess.entity.Cone;
import com.loneliess.entity.Point;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


public class ServiceValidation {
    private static final ServiceValidation instance=new ServiceValidation();
    private Validator validator;
    private ServiceValidation(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    public static ServiceValidation getInstance() { return instance; }

    public Set<ConstraintViolation<Cone>> validate(Cone data) {
        return validator.validate(data);
    }

    public Set<ConstraintViolation<Point>> validate(Point data) {
        return validator.validate(data);
    }
}
