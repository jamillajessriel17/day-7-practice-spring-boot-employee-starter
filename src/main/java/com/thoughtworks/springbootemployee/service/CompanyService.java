package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public List<Employee> getEmployeeRepositoryByCompanyId(Long id) {
        return companyRepository.getEmployeeRepositoryByCompanyId(id);
    }

    public List<Company> getCompanyListByPageNumberAndPageSize(Long pageNumber, Long pageSize) {
        return companyRepository.getCompanyListByPageNumberAndPageSize(pageNumber,pageSize);
    }

    public Company saveCompany(Company company) {
        return companyRepository.saveCompany(company);
    }

    public Company updateCompanyName(Long id, Company company) {
        return companyRepository.updateCompanyName(id, company);
    }

    public void deleteCompanyById(Long id) {
        companyRepository.deleteCompanyById(id);
    }

    public Company findById(Long id) {
       return  companyRepository.findById(id);
    }
}
