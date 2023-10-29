package com.example.springBootTesting.integration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public abstract class AbstractContainerBaseTest {

    static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:latest")
                .withUsername("username")
                .withPassword("password")
                .withDatabaseName("ems");

        MY_SQL_CONTAINER.start();
    }

//    @Container
//    private final static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest")
//            .withUsername("username")
//            .withPassword("password")
//            .withDatabaseName("ems");

    @DynamicPropertySource  // 애플리케이션 컨텍스트에 등록
    public static void dynamicPropertySource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
    }
}
