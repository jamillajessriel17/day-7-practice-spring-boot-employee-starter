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
    EmployeeRepository repository;

    @GetMapping
    public List<Employee> listAll() {
        return repository.getEmployees();
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> findByGender(@RequestParam String gender) {
        return repository.findByGender(gender);
    }

    @PostMapping
    public ResponseEntity<String> saveEmployee(@RequestBody  Employee employee){
        return new ResponseEntity<>(employee.getName()+" was added to the list of Employee.", HttpStatus.OK);
    }

}
