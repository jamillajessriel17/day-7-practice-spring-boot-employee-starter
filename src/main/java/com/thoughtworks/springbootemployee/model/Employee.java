package com.thoughtworks.springbootemployee.model;

public class Employee {

    private final long id;
    private final String name;
    private final Integer age;

    private final String gender;

    private final Integer salary;

    private final long companyId;

    public Employee(long id, String name, Integer age, String gender, Integer salary, long companyId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
    }

    public long getId() {
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

    public long getCompanyId() {
        return companyId;
    }

    public Integer getSalary() {
        return salary;
    }
}
