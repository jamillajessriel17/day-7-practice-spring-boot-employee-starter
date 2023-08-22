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
    public static final int START_ID_MINUS_ONE = 0;
    public static final int INCREMENENT_ID = 1;

    static {
        employees.add(new Employee(1l, "Alice", 30, "female", 3000, 1));
        employees.add(new Employee(2l, "Bob", 31, "male", 2000, 2));
        employees.add(new Employee(3l, "Carl", 32, "male", 6500, 1));
        employees.add(new Employee(4l, "David", 33, "male", 2000, 2));
        employees.add(new Employee(5l, "Ellen", 34, "female", 1000, 3));

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

    public void saveEmployee(Employee employee) {

        employees.add(new Employee(generateNextId(),
                employee.getName(),
                employee.getAge(),
                employee.getGender(),
                employee.getSalary(),
                employee.getCompanyId()));
    }

    private long generateNextId() {
        return employees.stream().mapToLong(Employee::getId)
                .max()
                .orElse(START_ID_MINUS_ONE) + INCREMENENT_ID;
    }

    public Employee updateEmployee(Employee employee, Long id) {
        int employeeIndexToBeUpdated = getEmployeeIndex(id);

        return employees.set(employeeIndexToBeUpdated, new Employee(id, employees.get(employeeIndexToBeUpdated).getName(),
                employee.getAge(),
                employees.get(employeeIndexToBeUpdated).getGender(),
                employee.getSalary(), employee.getCompanyId()));
    }

    private int getEmployeeIndex(Long id) {
        return employees.stream()
                .filter(employee1 -> id == employee1.getId())
                .mapToInt(employees::indexOf)
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public String deleteEmployeeById(long id) {
        int employeeIndexToBeDeleted = getEmployeeIndex(id);

        return employees.remove(employeeIndexToBeDeleted).getName();
    }

    public List<Employee> findEmployeeByPageNumberAndPageSize(long pageNumber, long pageSize) {
        return employees.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

    }
}
