package com.bjtu.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AcountUtilsTest {

    @Autowired
    AcountUtils acountUtils;

    @Test
    void userIsExist() {
        System.out.println(acountUtils.userIsExist("21001002"));
    }

    @Test
    void isMatchEmail() {
        System.out.println(acountUtils.isMatchEmail("21001002","2461172547@qq.com"));
    }
}