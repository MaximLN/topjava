package ru.javawebinar.topjava.repository.jdbc;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class JdbcValidation {

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    protected <T> T elementValidation(T t) {
        Set<ConstraintViolation<T>> violations = validator.validate(t);
        return violations.size() == 0 ? t : null;
    }

    protected <T> List<T> groupValidation(List<T> uncheckedList) {
        List<T> checkedList = new ArrayList<>();
        for (T element : uncheckedList) {
            Set<ConstraintViolation<T>> violations = validator.validate(element);
            if (violations.size() == 0) {
                checkedList.add(element);
            }
        }
        return checkedList;
    }
}
