package com.thoughtworks.springbootemployee.repository;

public class EmployeeNotFoundException extends  RuntimeException{
    public EmployeeNotFoundException() {
        super("Employee not found.");
    }
}
