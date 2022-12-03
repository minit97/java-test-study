package com.example.javateststudy;

import antlr.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTest {
    @Test
    void test() {
        List<String> listA = new ArrayList<String>();

        listA.add("김삿갓");
        listA.add("홍아리");
        listA.add(new String("홍길동"));

        listA.add(1, "1번째 요소값");
        listA.remove("김삿갓");

        System.out.println(listA);

    }

    @Test
    void test2() {

        HashMap<String,Object> a = new HashMap<>();

        String test =String.valueOf(a.get("mem_level"));
        System.out.println(a.get("mem_level"));
        System.out.println(test.length());
    }
}
