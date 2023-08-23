package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.EmployeeInactiveException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
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
                .assertThrows(EmployeeCreateException.class, () -> {
                    employeeService.saveEmployee(employee);
                });
        //then
        Assertions.assertEquals("Employee must be 18~65 years old.", employeeCreateException.getMessage());
    }

    @Test
    void should_throw_exception_when_saveEmployee_given_employee_service_and_employee_age_is_less_than_65() {
        //given
        Employee employee = new Employee(null, "Jessriel", 66, "male", 102389, 2L);
        //when
        EmployeeCreateException employeeCreateException = Assertions
                .assertThrows(EmployeeCreateException.class, () -> {
                    employeeService.saveEmployee(employee);
                });
        //then
        Assertions.assertEquals("Employee must be 18~65 years old.", employeeCreateException.getMessage());
    }

    @Test
    void should_set_employee_active_status_to_true_by_default_when_create_employee_given_employee_service() {
        //given
        Employee employee = new Employee(null, "Jessriel", 34, "male", 102389, 2L);
        when(mockedEmployeeRepository.saveEmployee(employee)).thenReturn(employee);
        //when
        Employee savedEmployeeResponse = employeeService.saveEmployee(employee);
        //then
        Assertions.assertTrue(savedEmployeeResponse.isActiveStatus());
    }

    @Test
    void should_update_employee_active_status_to_false_when_deleteEmployee_given_employee_service_employee_id() {
        //given
        Employee employee = new Employee(1L, "Jessriel", 34, "male", 102389, 2L);
        when(mockedEmployeeRepository.findById(1L)).thenReturn(employee);
        //when
        employeeService.deleteEmployeeById(employee.getId());
        //then
        verify(mockedEmployeeRepository).updateEmployee(argThat((tempEmployee) -> {
            Assertions.assertFalse(tempEmployee.isActiveStatus());
            Assertions.assertEquals(employee.getId(), tempEmployee.getId());
            Assertions.assertEquals(employee.getName(), tempEmployee.getName());
            Assertions.assertEquals(employee.getAge(), tempEmployee.getAge());
            Assertions.assertEquals(employee.getGender(), tempEmployee.getGender());
            Assertions.assertEquals(employee.getSalary(), tempEmployee.getSalary());
            return true;
        }), eq(employee.getId()));
    }

    @Test
    void should_return_EmployeeInactiveException_when_updateEmployee_given_employee_service() {
        //given
        Employee employee = new Employee(1L, "Jessriel", 33, "male", 2999, 1L, Boolean.FALSE);
        when(mockedEmployeeRepository.findById(employee.getId())).thenReturn(employee);
        //when
        EmployeeInactiveException employeeInactiveException = Assertions.assertThrows(EmployeeInactiveException.class, () -> {
            employeeService.updateEmployee(employee, employee.getId());
        });
        //then
        Assertions.assertEquals("Employee is inactive.", employeeInactiveException.getMessage());

    }

    @Test
    void should_return_all_employees_when_getAllEmployees() {
        //given
        Employee employee = new Employee(null, "Jessriel", 23,"male",3435,1L);
        Employee employee1 = new Employee(null, "Jamilla", 22,"male",342343,1L);
        //when
        when(mockedEmployeeRepository.getEmployees()).thenReturn(List.of(employee,employee1));
        //then
        List<Employee> allEmployees = employeeService.getAllEmployees();
        Assertions.assertEquals(employee, allEmployees.get(0));
        Assertions.assertEquals(employee1, allEmployees.get(1));
    }
}