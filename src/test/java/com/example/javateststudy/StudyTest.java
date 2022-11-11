package com.example.javateststudy;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)   // "_" -> " " 변경
class StudyTest {

    @FastTest
    @DisplayName("assertj")
    void assertj() {
        Study actual = new Study(10);
        assertThat(actual.getLimit()).isGreaterThan(0);
    }

    @SlowTest
    @DisplayName("스터디 만들기3")
    @EnabledOnOs({OS.MAC,OS.LINUX})
    @EnabledOnJre({JRE.JAVA_8,JRE.JAVA_11})
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    void create_new_study3() {
        String test_env = System.getenv("TEST_ENV");
        System.out.println("local");
        Study actual = new Study(100);
        assertThat(actual.getLimit()).isGreaterThan(0);
    }

    @Test
    @DisplayName("스터디 만들기")
    @DisabledOnOs(OS.MAC)
    void create_new_study() {

        String test_env = System.getenv("TEST_ENV");
        System.out.println(test_env);
        assumeTrue("LOCAL".equalsIgnoreCase(test_env));    // 테스트 환경이 로컬일때만 다음 코드를 실행한다.
        Study study = new Study(-10);

        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
            Study actual = new Study(100);
            assertThat(actual.getLimit()).isGreaterThan(0);
        }); // 조건에 따른 로직 수행

        assumingThat("DEV".equalsIgnoreCase(test_env), () -> {
            Study actual = new Study(10);
            assertThat(actual.getLimit()).isGreaterThan(0);
        }); // 조건에 따른 로직 수행

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