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
                .andExpect(jsonPath("$[0].gender").value(jess.getGender()))
                .andExpect(jsonPath("$[0].salary").value(jess.getSalary()));
        //then
    }
    @Test
    void should_return_the_employee_when_perform_get_employee_given_an_employee_id() throws Exception {
    //given
        employeeRepository.saveEmployee(new Employee(1L, "Jess", 23, "Male", 2000, 1L));
        Employee alice = employeeRepository.saveEmployee(new Employee(2L, "Alic", 23, "female", 2000, 1L));
     //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/"+ alice.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(alice.getId()))
                .andExpect(jsonPath("$.name").value(alice.getName()))
                .andExpect(jsonPath("$.age").value(alice.getAge()))
                .andExpect(jsonPath("$.gender").value(alice.getGender()))
                .andExpect(jsonPath("$.salary").value(alice.getSalary()));
     //then
    }
}
