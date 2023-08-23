package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping
    public List<Company> getCompanyList() {

        return companyRepository.findAll();
    }

    @GetMapping("/{id}")
    public Company findCompanyById(@PathVariable Long id) {
        return companyRepository.findById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeeListByCompanyId(@PathVariable Long id) {

        return companyRepository.getEmployeeRepositoryByCompanyId(id);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> getCompanyListByPageNumberAndPageSize(@RequestParam long pageNumber, @RequestParam long pageSize) {
        return companyRepository.getCompanyListByPageNumberAndPageSize(pageNumber, pageSize);
    }

    @PostMapping
    public ResponseEntity<String> saveCompany(@RequestBody Company company) {
        Company saveCompany = companyRepository.saveCompany(company);
        return new ResponseEntity<>(saveCompany.getName() + " was saved", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateCompanyName(@PathVariable long id, @RequestBody Company company) {
        Company udpateCompany = companyRepository.updateCompanyName(id, company);
        return new ResponseEntity<>(udpateCompany.getName() + " name was updated.", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyById(@PathVariable long id) {
        companyRepository.deleteCompanyById(id);

    }


}
