package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.thoughtworks.springbootemployee.repository.EmployeeRepository.INCREMENT_ID;
import static com.thoughtworks.springbootemployee.repository.EmployeeRepository.START_ID_MINUS_ONE;

@Repository
public class CompanyRepository {
    private static final List<Company> companyList = new ArrayList<>();

    @Autowired
    EmployeeRepository employeeRepository;

    static {
        companyList.add(new Company(1L, "OOCL"));
        companyList.add(new Company(2L, "KSK company"));
        companyList.add(new Company(3L, "JLJ company"));
        companyList.add(new Company(4L, "ACCENTURE"));
        companyList.add(new Company(5L, "Micro sourcing"));
        companyList.add(new Company(6L, "Krusty Crab"));
    }


    public List<Company> findAll() {
        return companyList;
    }

    public Company findById(Long id) {

        return companyList.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);
    }

    public List<Employee> getEmployeeRepositoryByCompanyId(Long companyId) {
        return employeeRepository.getEmployees()
                .stream().filter(employee -> employee.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
    }

    public List<Company> getCompanyListByPageNumberAndPageSize(Long pageNumber, Long pageSize) {
        return companyList.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company saveCompany(Company company) {
        Company company1 = new Company(generateNextId(), company.getName());
        companyList.add(company1);
        return company1;
    }

    public Company updateCompanyName(Long id, Company company) {
        int companyIndexTobeUpdated = getCompanyIndex(id);
        Company updatedCompany = new Company(id, company.getName());
         companyList.set(companyIndexTobeUpdated, updatedCompany);
        return updatedCompany;

    }
    public void deleteCompanyById(Long id) {
        int companyIndexToBeDeleted = getCompanyIndex(id);

        companyList.remove(companyIndexToBeDeleted);
    }

    private int getCompanyIndex(Long id) {
        return companyList.stream()
                .filter(company -> company.getId().equals(id))
                .mapToInt(companyList::indexOf)
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);
    }

    private Long generateNextId() {
        return companyList.stream().mapToLong(Company::getId)
                .max()
                .orElse(START_ID_MINUS_ONE) + INCREMENT_ID;
    }
}
