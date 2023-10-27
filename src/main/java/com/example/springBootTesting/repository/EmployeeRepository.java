package com.example.springBootTesting.repository;

import com.example.springBootTesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
