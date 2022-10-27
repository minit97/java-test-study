package com.example.javateststudy;

import org.junit.jupiter.api.*;

class StudyTest {

    @Test
    void create() {
        Study study = new Study();
        System.out.println("test1");
    }

    @Test
    void create1() {
        Study study = new Study();
        System.out.println("test2");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("before all");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("Before each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("After each");
    }
}