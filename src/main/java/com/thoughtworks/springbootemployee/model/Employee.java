package com.thoughtworks.springbootemployee.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {

    public static final int MIN_VALID_AGE = 18;
    public static final int MAX_VALID_AGE = 65;
    private final Long id;
    private final String name;
    private final Integer age;

    private final String gender;

    private final Integer salary;

    private final Long companyId;

    private boolean activeStatus;


    public Employee(Long id, String name, Integer age, String gender, Integer salary, Long companyId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
    }

    @JsonCreator
    public Employee(@JsonProperty("id") Long id,
                    @JsonProperty("name") String name,
                    @JsonProperty("age") Integer age,
                    @JsonProperty("gender") String gender,
                    @JsonProperty("salary") Integer salary,
                    @JsonProperty("companyId") Long companyId,
                    @JsonProperty("activeStatus") boolean activeStatus) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
        this.activeStatus = activeStatus;
    }

    public Long getId() {
        return id;
    }

    public boolean hasValidAge() {
        return getAge() <= MIN_VALID_AGE || getAge() >= MAX_VALID_AGE;
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

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }
}
