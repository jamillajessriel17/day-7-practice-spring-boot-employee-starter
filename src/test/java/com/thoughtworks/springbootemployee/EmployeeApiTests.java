package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
    void cleanEmployeeRepository() {
        employeeRepository.cleanAll();
    }

    @Test
    void should_return_all_employees_when_get_employees_given_some_employee() throws Exception {
        //given
        Employee jess = employeeRepository.saveEmployee(new Employee(1L, "Jess", 23, "Male", 2000, 1L));
        //when     //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(jess.getId()))
                .andExpect(jsonPath("$[0].name").value(jess.getName()))
                .andExpect(jsonPath("$[0].age").value(jess.getAge()))
                .andExpect(jsonPath("$[0].gender").value(jess.getGender()))
                .andExpect(jsonPath("$[0].salary").value(jess.getSalary()));
    }

    @Test
    void should_return_the_employee_when_perform_get_employee_given_an_employee_id() throws Exception {
        //given
        employeeRepository.saveEmployee(new Employee(1L, "Jess", 23, "Male", 2000, 1L));
        Employee alice = employeeRepository.saveEmployee(new Employee(2L, "Alic", 23, "female", 2000, 1L));
        //when //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + alice.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(alice.getId()))
                .andExpect(jsonPath("$.name").value(alice.getName()))
                .andExpect(jsonPath("$.age").value(alice.getAge()))
                .andExpect(jsonPath("$.gender").value(alice.getGender()))
                .andExpect(jsonPath("$.salary").value(alice.getSalary()));
    }

    @Test
    void should_return_404_not_found_when_perform_get_employee_given_a_not_exist_id() throws Exception {
        //given
        long notExistedId = 99L;
        //when //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + notExistedId))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_the_employees_when_perform_get_employees_given_gender_a_gender() throws Exception {
        //given
        employeeRepository.saveEmployee(new Employee(1L, "Jess", 23, "Male", 2000, 1L));
        Employee alice = employeeRepository.saveEmployee(new Employee(2L, "Alice", 23, "Female", 2000, 1L));
        //when   //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees").param("gender", "Female"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(alice.getId()))
                .andExpect(jsonPath("$[0].name").value(alice.getName()))
                .andExpect(jsonPath("$[0].age").value(alice.getAge()))
                .andExpect(jsonPath("$[0].gender").value(alice.getGender()))
                .andExpect(jsonPath("$[0].salary").value(alice.getSalary()));

    }

    @Test
    void should_return_the_created_employee_when_perform_post_given_new_employee() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employeeToSave = new Employee(1L, "Jess", 23, "Male", 2000, 1L);
        //when //then
        mockMvcClient.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeToSave)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(employeeToSave.getId()))
                .andExpect(jsonPath("$.name").value(employeeToSave.getName()))
                .andExpect(jsonPath("$.age").value(employeeToSave.getAge()))
                .andExpect(jsonPath("$.gender").value(employeeToSave.getGender()))
                .andExpect(jsonPath("$.salary").value(employeeToSave.getSalary()));

    }

    @Test
    void should_return_updated_employee_when_perform_put_given_employee_id_and_new_employee_age_and_salary() throws Exception {
        //given
        Employee employeeToBeUpdate = employeeRepository.saveEmployee(new Employee(1L, "Jess", 23, "Male", 2000, 1L));
        Employee employeeDetails = new Employee(1L,"",26,"",70000,1L);
        ObjectMapper objectMapper = new ObjectMapper();
        //when //then
        mockMvcClient.perform(MockMvcRequestBuilders.put("/employees/"+employeeDetails.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDetails)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(employeeToBeUpdate.getId()))
                .andExpect(jsonPath("$.name").value(employeeToBeUpdate.getName()))
                .andExpect(jsonPath("$.age").value(employeeDetails.getAge()))
                .andExpect(jsonPath("$.gender").value(employeeToBeUpdate.getGender()))
                .andExpect(jsonPath("$.salary").value(employeeDetails.getSalary()));
    }
}
