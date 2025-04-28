package com.ricardo.gym.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ValidationErrorResponse extends ErrorResponse {
    private List<FieldValidationError> errors = new ArrayList<>();

    public void addError(String field, String message) {
        errors.add(new FieldValidationError(field, message));
    }
}