package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
            if(employee.hasValidAge()){
                throw new EmployeeCreateException();
            }
            employee.setActiveStatus(Boolean.TRUE);
        return employeeRepository.saveEmployee(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id);
        employee.setActiveStatus(Boolean.FALSE);
        employeeRepository.updateEmployee(employee, id);
    }

}
