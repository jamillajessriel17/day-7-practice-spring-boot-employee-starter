package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private static final List<Company> companyList = new ArrayList<>();

    @Autowired
    EmployeeRepository employeeRepository;

    static {
        companyList.add(new Company(1, "OOCL"));
        companyList.add(new Company(2, "KSK company"));
        companyList.add(new Company(3, "JLJ company"));
        companyList.add(new Company(4, "ACCENTURE"));
        companyList.add(new Company(5, "Micro sourcing"));
        companyList.add(new Company(6, "Krusty Crab"));
    }


    public List<Company> findAll() {
        return companyList;
    }

    public Company findById(Long id) {

        return companyList.stream()
                .filter(company -> company.getId() == id)
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> getEmployeeRepositoryByCompanyId(long companyId) {
        return employeeRepository.getEmployees()
                .stream().filter(employee -> employee.getCompanyId() == companyId)
                .collect(Collectors.toList());
    }

    public List<Company> getCompanyListByPageNumberAndPageSize(long pageNumber, long pageSize) {
        return companyList.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company saveCompany(Company company) {

         companyList.add(company);
        return company;
    }
}
