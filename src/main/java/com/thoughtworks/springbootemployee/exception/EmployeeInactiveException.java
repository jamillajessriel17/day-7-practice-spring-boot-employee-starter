package com.thoughtworks.springbootemployee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmployeeInactiveException extends RuntimeException {
    public EmployeeInactiveException() {
        super("Employee is inactive.");
    }
}
