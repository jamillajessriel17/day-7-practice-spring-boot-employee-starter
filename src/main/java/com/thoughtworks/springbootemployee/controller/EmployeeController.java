package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> listAll() {
        return employeeRepository.getEmployees();
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable Long id) {
        return employeeRepository.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> findByGender(@RequestParam String gender) {
        return employeeRepository.findByGender(gender);
    }

    @PostMapping
    public ResponseEntity<String> saveEmployee(@RequestBody Employee employee) {
        employeeRepository.saveEmployee(employee);
        return new ResponseEntity<>(employee.getName() + " was added to the list of Employee.", HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updatedEmployee = employeeRepository.updateEmployee(employee, id);
        return new ResponseEntity<>(updatedEmployee.getName() + " was updated", HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeById(@PathVariable long id) {
        employeeRepository.deleteEmployeeById(id);

    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Employee> findEmployeeByPageNumberAndPageSize(@RequestParam long pageNumber, @RequestParam long pageSize) {

        return employeeRepository.findEmployeeByPageNumberAndPageSize(pageNumber, pageSize);
    }

}
