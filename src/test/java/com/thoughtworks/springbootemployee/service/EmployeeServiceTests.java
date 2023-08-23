package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class EmployeeServiceTests {
    private EmployeeService employeeService;

    private EmployeeRepository mockedEmployeeRepository;

    @BeforeEach
    void setUp() {
        mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeService(mockedEmployeeRepository);
    }

    @Test
    void should_return_saved_employee_when_saveEmployee_given_employee_service_and_employee_valid_age() {
        //given
        Employee employee = new Employee(null, "Jessriel", 25, "male", 102389, 2L);
        Employee savedEmployee = new Employee(1L, "Jessriel", 25, "male", 102389, 2L);
        when(mockedEmployeeRepository.saveEmployee(employee)).thenReturn(savedEmployee);
        //when
        Employee savedEmployeeResponse = employeeService.saveEmployee(employee);
        //then
        Assertions.assertEquals(savedEmployee.getId(), savedEmployeeResponse.getId());
        Assertions.assertEquals(savedEmployee.getName(), savedEmployeeResponse.getName());
        Assertions.assertEquals(savedEmployee.getAge(), savedEmployeeResponse.getAge());
        Assertions.assertEquals(savedEmployee.getGender(), savedEmployeeResponse.getGender());
        Assertions.assertEquals(savedEmployee.getSalary(), savedEmployeeResponse.getSalary());

    }

    @Test
    void should_throw_exception_when_saveEmployee_given_employee_service_and_employee_age_is_less_than_18() {
        //given
        Employee employee = new Employee(null, "Jessriel", 15, "male", 102389, 2L);
        //when
        EmployeeCreateException employeeCreateException = Assertions
                .assertThrows(EmployeeCreateException.class,() -> {
                            employeeService.saveEmployee(employee);
                        });
        //then
        Assertions.assertEquals("Employee must be 18~65 years old.", employeeCreateException.getMessage());
    }
}
