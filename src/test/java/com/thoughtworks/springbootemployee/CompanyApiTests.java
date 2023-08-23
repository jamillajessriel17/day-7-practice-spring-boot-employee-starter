package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
}
