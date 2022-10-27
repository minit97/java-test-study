package com.example.javateststudy;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)   // "_" -> " " 변경
class StudyTest {

    @Test
    @DisplayName("assertj")
    void assertj() {
        Study actual = new Study(10);
        org.assertj.core.api.Assertions.assertThat(actual.getLimit()).isGreaterThan(0);
    }

    @Test
    @DisplayName("스터디 만들기")
    void create_new_study() {
        Study study = new Study(-10);

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), new Supplier<String>() {
                    @Override
                    public String get() {
                        return "스터디를 처음 만들면 DRAFT 상태다.";
                    }
                }),
                () -> assertTrue(study.getLimit() > 0,"스터디 최대 참석 가능 인원은 0보다 커야 한다. ")
        );
    }

    @Test
    void create_new_study_again() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        assertEquals("limit은 0보다 커야한다.", exception.getMessage());
    }

    @Test
    void create_new_study2() {
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {   // Preemptively 는 기대한 값에 멈춤
            new Study(10);
            Thread.sleep(1000);
        });
        // ThreadLocal 쓴다면 예상치못한 결과가 나올 수 있다.
        // 그렇기에 ThreadLocal이나 Thread와 관련없는 코드를 실행 시 괜찮다.
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