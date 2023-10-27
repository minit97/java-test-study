package com.example.springBootTesting.service;

import com.example.springBootTesting.exception.ResourceNotFoundException;
import com.example.springBootTesting.model.Employee;
import com.example.springBootTesting.repository.EmployeeRepository;
import com.example.springBootTesting.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;


    private Employee employee;

    @BeforeEach
    void setup() {
//        employeeRepository = Mockito.mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstName("p")
                .lastName("hm")
                .email("phm@naver.com")
                .build();
    }

    @Test
    @DisplayName("saveEmployee 성공 케이스")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        // given
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        // when
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    @DisplayName("saveEmployee 실패 케이스")
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
        // given
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        // when
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        // then
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("리스트 전체 조회 성공")
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {
        // given
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("1")
                .lastName("23")
                .email("123@naver.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        // when
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("리스트가 비어있을 경우")
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
        // given
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("1")
                .lastName("23")
                .email("123@naver.com")
                .build();

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("단건 조회")
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        // given
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when
        Employee findEmployee = employeeService.getEmployeeById(employee.getId()).get();

        // then
        assertThat(findEmployee).isNotNull();
    }

    @Test
    @DisplayName("수정")
    void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdateEmployee() {
        // given
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("test@naver.com");
        employee.setFirstName("first");

        // when
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // then
        assertThat(updatedEmployee.getEmail()).isEqualTo("test@naver.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("first");
    }

    @Test
    @DisplayName("삭제")
    void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        // given
        willDoNothing().given(employeeRepository).deleteById(1L);

        // when
        employeeService.deleteEmployee(1L);

        // then
        verify(employeeRepository, times(1)).deleteById(1L);
    }
}