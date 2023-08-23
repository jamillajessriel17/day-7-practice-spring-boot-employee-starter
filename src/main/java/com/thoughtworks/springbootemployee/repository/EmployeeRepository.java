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
    public static final int INCREMENT_ID = 1;

    static {
        employees.add(new Employee(1L, "Alice", 30, "female", 3000, 1L));
        employees.add(new Employee(2L, "Bob", 31, "male", 2000, 2L));
        employees.add(new Employee(3L, "Carl", 32, "male", 6500, 1L));
        employees.add(new Employee(4L, "David", 33, "male", 2000, 2L));
        employees.add(new Employee(5L, "Ellen", 34, "female", 1000, 3L));

    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Employee findById(Long id) {

        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee saveEmployee(Employee employee) {

        Employee employee1 = new Employee(generateNextId(),
                employee.getName(),
                employee.getAge(),
                employee.getGender(),
                employee.getSalary(),
                employee.getCompanyId());
        employee1.setActiveStatus(employee.isActiveStatus());
        employees.add(employee1);

        return employee1;
    }

    private long generateNextId() {
        return employees.stream().mapToLong(Employee::getId)
                .max()
                .orElse(START_ID_MINUS_ONE) + INCREMENT_ID;
    }

    public Employee updateEmployee(Employee employee, Long id) {

        int employeeIndexToBeUpdated = getEmployeeIndex(id);
        employees.set(employeeIndexToBeUpdated, new Employee(id, employees.get(employeeIndexToBeUpdated).getName(),
                employee.getAge(),
                employees.get(employeeIndexToBeUpdated).getGender(),
                employee.getSalary(),
                employee.getCompanyId(),
                employee.isActiveStatus()));
        return employees.get(employeeIndexToBeUpdated);
    }

    private int getEmployeeIndex(Long id) {
        return employees.stream()
                .filter(employee1 -> id == employee1.getId())
                .mapToInt(employees::indexOf)
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public void deleteEmployeeById(Long id) {
        int employeeIndexToBeDeleted = getEmployeeIndex(id);
        employees.remove(employeeIndexToBeDeleted);
    }

    public List<Employee> findEmployeeByPageNumberAndPageSize(Long pageNumber, Long pageSize) {
        return employees.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

    }


    public void cleanAll() {
        employees.clear();
    }
}
