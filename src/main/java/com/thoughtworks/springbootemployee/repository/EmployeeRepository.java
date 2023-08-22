package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private static final List<Employee> employees = new ArrayList<>();
  static {

      employees.add(new Employee(1L, "Jess", 25, "male", 134324));
      employees.add(new Employee(2L, "Jessr", 25, "male", 134324));
      employees.add(new Employee(3L, "Alice", 25, "female", 134324));
      employees.add(new Employee(4L, "Leah", 25, "female", 134324));
      employees.add(new Employee(5L, "Jessriel", 25, "male", 134324));
  }

    public  List<Employee> getEmployees() {
        return employees;
    }

    public Employee findById(Long id) {

      return employees.stream()
              .filter(employee -> employee.getId() ==id)
              .findFirst()
              .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender() .equals(gender))
                .collect(Collectors.toList());
    }
}
