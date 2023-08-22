package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {


    EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<Employee> listAll() {
        return repository.getEmployees();
    }
    @GetMapping("/{id}")
    public Employee findById(@PathVariable Long id){
        return repository.findById(id);
    }
    @GetMapping(params = {"gender"})
    public List<Employee> findByGender(@RequestParam String gender){
        return repository.findByGender(gender);
    }




}
