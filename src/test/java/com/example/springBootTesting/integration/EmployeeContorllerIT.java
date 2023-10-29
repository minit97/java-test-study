package com.example.springBootTesting.integration;

import com.example.springBootTesting.model.Employee;
import com.example.springBootTesting.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeContorllerIT extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();
    }

    @Test
    @DisplayName("저장 통합테스트")
    void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("p")
                .lastName("hm")
                .email("phm@naver.com")
                .build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    @Test
    @DisplayName("리스트 조회 통합테스트")
    void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
        // given
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("p").lastName("hm").email("phm@naver.com").build());
        listOfEmployees.add(Employee.builder().firstName("p1").lastName("hm1").email("p1hm1@naver.com").build());
        employeeRepository.saveAll(listOfEmployees);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listOfEmployees.size())));
    }

    @Test
    @DisplayName("단건 조회 통합테스트 - 성공")
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("p")
                .lastName("hm")
                .email("phm@naver.com")
                .build();
        employeeRepository.save(employee);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", employee.getId()));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    @Test
    @DisplayName("단건 조회 통합테스트 - 실패")
    void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {
        // given

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", 0L));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("수정 통합테스트 - 성공")
    void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("p")
                .lastName("hm")
                .email("phm@naver.com")
                .build();
        employeeRepository.save(employee);
        Employee updatedEmployee = Employee.builder()
                .firstName("p1")
                .lastName("hm2")
                .email("p1hm2@naver.com")
                .build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(updatedEmployee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(updatedEmployee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(updatedEmployee.getEmail())));
    }

    @Test
    @DisplayName("수정 통합테스트 - 실패")
    void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("p")
                .lastName("hm")
                .email("phm@naver.com")
                .build();
        employeeRepository.save(employee);
        Employee updatedEmployee = Employee.builder()
                .firstName("p1")
                .lastName("hm2")
                .email("p1hm2@naver.com")
                .build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", 0L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("삭제 통합테스트")
    void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("p")
                .lastName("hm")
                .email("phm@naver.com")
                .build();
        employeeRepository.save(employee);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{id}", employee.getId()));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
