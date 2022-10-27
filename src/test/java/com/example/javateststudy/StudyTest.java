package com.example.javateststudy;

import org.junit.jupiter.api.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)   // "_" -> " " 변경
class StudyTest {

    @Test
    @DisplayName("스터디 만들기")
    void create_new_study() {
        Study study = new Study();
        System.out.println("test1");
    }

    @Test
    void create_new_study_again() {
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