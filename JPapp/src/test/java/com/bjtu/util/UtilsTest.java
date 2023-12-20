package com.bjtu.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UtilsTest {

    @Autowired
    Utils utils;

    @Test
    void userIsExist() {
        System.out.println(utils.userIsExist("21001002"));
    }

    @Test
    void isMatchEmail() {
        System.out.println(utils.isMatchEmail("21001002","2461172547@qq.com"));
    }
}