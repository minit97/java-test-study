package com.example.springBootTesting.repository;

import com.example.springBootTesting.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryITest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("테스트")
    void givenEmloyeeObject_whenSave_thenReturnSavedEmployee() {
        // given
        Employee build = Employee.builder()
                .firstName("p")
                .lastName("hm")
                .email("phm@naver.com")
                .build();

        // when
        Employee saved = employeeRepository.save(build);

        // then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isGreaterThan(0);
    }

}