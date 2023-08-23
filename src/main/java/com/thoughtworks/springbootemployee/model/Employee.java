package com.thoughtworks.springbootemployee.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Employee {

    private final Long id;
    private final String name;
    private final Integer age;

    private final String gender;

    private final Integer salary;

    private final Long companyId;

    public Employee(Long id, String name, Integer age, String gender, Integer salary, Long companyId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Integer getSalary() {
        return salary;
    }
}
