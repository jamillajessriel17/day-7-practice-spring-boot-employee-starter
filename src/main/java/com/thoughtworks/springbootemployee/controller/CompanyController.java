package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    CompanyService companyService;
    @GetMapping
    public List<Company> getCompanyList() {

        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public Company findCompanyById(@PathVariable Long id) {
        return companyService.findById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeeListByCompanyId(@PathVariable Long id) {

        return companyService.getEmployeeRepositoryByCompanyId(id);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> getCompanyListByPageNumberAndPageSize(@RequestParam Long pageNumber, @RequestParam Long pageSize) {
        return companyService.getCompanyListByPageNumberAndPageSize(pageNumber, pageSize);
    }

    @PostMapping
    public ResponseEntity<Company> saveCompany(@RequestBody Company company) {
        Company savedCompany = companyService.saveCompany(company);
        return new ResponseEntity<>(savedCompany, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Company> updateCompanyName(@PathVariable Long id, @RequestBody Company company) {
        Company udpatedCompany = companyService.updateCompanyName(id, company);
        return new ResponseEntity<>(udpatedCompany, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyById(@PathVariable Long id) {
        companyService.deleteCompanyById(id);
    }


}
