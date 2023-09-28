package com.example.tdd_start.chap2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    @Test
    void plus() {
        int result = Calculator.plus(1, 2);
        assertEquals(3, result);
        assertEquals(5, Calculator.plus(4, 1));
    }
}