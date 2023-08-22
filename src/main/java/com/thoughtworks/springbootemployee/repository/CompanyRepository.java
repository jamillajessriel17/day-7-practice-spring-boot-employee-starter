package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private static final List<Company> companyList = new ArrayList<>();

    static {
        companyList.add(new Company(1, "OOCL"));
        companyList.add(new Company(2, "KSK company"));
        companyList.add(new Company(3, "JLJ company"));
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
}
