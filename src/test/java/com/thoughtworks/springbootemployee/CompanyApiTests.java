package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class CompanyApiTests {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    private MockMvc mockMvcClient;

    @BeforeEach
    void cleanCompanyRepository() {
        companyRepository.cleanAll();
    }

    @Test
    void should_return_all_companies_when_perform_get_companies() throws Exception {
        //given
        Company helloCompany = companyRepository.saveCompany(new Company("Hello company"));
        Company hiCompany = companyRepository.saveCompany(new Company("Hi company"));
        //when //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(helloCompany.getId()))
                .andExpect(jsonPath("$[0].name").value(helloCompany.getName()))
                .andExpect(jsonPath("$[1].id").value(hiCompany.getId()))
                .andExpect(jsonPath("$[1].name").value(hiCompany.getName()));
    }

    @Test
    void should_return_company_when_perform_get_companies_given_company_id() throws Exception {
        //given
        Company helloCompany = companyRepository.saveCompany(new Company("Hello company"));
        companyRepository.saveCompany(new Company("Hi company"));
        //when //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies/" + helloCompany.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Hello company"));
    }

    @Test
    void should_return_employee_list_when_perform_get_employees_by_company_id() throws Exception {
        //given
        Long companyId = 1L;
        Employee jessriel = new Employee(null, "Jessriel", 23, "male", 345, 1L);
        Employee lucy = new Employee(null, "lucy", 24, "female", 234, 2L);
        Employee dan = new Employee(null, "Dan", 19, "male", 42335, 1L);

        companyRepository.getEmployeeRepository().cleanAll();
        Employee jessrielEmployee = companyRepository.getEmployeeRepository().saveEmployee(jessriel);
        companyRepository.getEmployeeRepository().saveEmployee(lucy);
        Employee danEmployee = companyRepository.getEmployeeRepository().saveEmployee(dan);
        //when  //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies/" + companyId + "/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(jessrielEmployee.getId()))
                .andExpect(jsonPath("$[0].name").value(jessrielEmployee.getName()))
                .andExpect(jsonPath("$[1].id").value(danEmployee.getId()))
                .andExpect(jsonPath("$[1].name").value(danEmployee.getName()));

    }

    @Test
    void should_return_company_list_when_perform_get_companies_given_page_number_page_size() throws Exception {
        //given
        companyRepository.saveCompany(new Company("Hello"));
        companyRepository.saveCompany(new Company("Hello1"));
        companyRepository.saveCompany(new Company("Hello2"));
        Company hello3Company = companyRepository.saveCompany(new Company("Hello3"));
        Company hello4Company = companyRepository.saveCompany(new Company("Hello4"));
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies")
                        .param("pageNumber", "2")
                        .param("pageSize", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(hello3Company.getId()))
                .andExpect(jsonPath("$[0].name").value(hello3Company.getName()))
                .andExpect(jsonPath("$[1].id").value(hello4Company.getId()))
                .andExpect(jsonPath("$[1].name").value(hello4Company.getName()));
        ;
        ;
        //then
    }
}
