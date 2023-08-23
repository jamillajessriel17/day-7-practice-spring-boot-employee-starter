package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeApiTests {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvcClient;

    @BeforeEach
    void cleanEmployeeRepository(){
        employeeRepository.cleanAll();
    }
    @Test
    void should_return_all_employees_when_get_employees_given_some_employee() throws Exception {
        //given
        Employee jess = employeeRepository.saveEmployee(new Employee(1L, "Jess", 23, "Male", 2000, 1L));
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(jess.getId()))
                .andExpect(jsonPath("$[0].name").value(jess.getName()))
                .andExpect(jsonPath("$[0].age").value(jess.getAge()))
                .andExpect(jsonPath("$[0].gender").value(jess.getGender()));
        //then
    }

}
