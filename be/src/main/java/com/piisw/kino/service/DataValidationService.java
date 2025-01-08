package com.piisw.kino.service;

import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.piisw.kino.rest.exceptions.DataValidationException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@Component
public class DataValidationService {
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public <T> void throwOnInvalidData(T obj) throws ResponseStatusException {
        if(obj == null)
            throw new DataValidationException("Data is null");

        Set<ConstraintViolation<T>> violations = validator.validate(obj);

        if(violations.size() > 0) {
            StringBuffer violationInfo = new StringBuffer();
            violations.forEach((c) -> violationInfo.append(c.getMessage() + "; "));

            throw new DataValidationException(violationInfo.toString());
        }
    }
}
