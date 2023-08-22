package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private static final List<Employee> employees = new ArrayList<>();

    static {
        employees.add(new Employee(1l, "Alice", 30, "Female", 3000));
        employees.add(new Employee(2l, "Bob", 31, "Male", 2000));
        employees.add(new Employee(3l, "Carl", 32, "Male", 6500));
        employees.add(new Employee(4l, "David", 33, "Male", 2000));
        employees.add(new Employee(5l, "Ellen", 34, "Female", 1000));

    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Employee findById(Long id) {

        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee saveEmployee(Employee employee) {
        employees.add(employee);
        return employee;
    }

    public Employee updateEmployee(Employee employee, Long id) {
        int employeeIndexToBeUpdated = getEmployeeIndex(id);

        return employees.set(employeeIndexToBeUpdated, new Employee(id, employees.get(employeeIndexToBeUpdated).getName(),
                employee.getAge(),
                employees.get(employeeIndexToBeUpdated).getGender(),
                employee.getSalary()));
    }
}
