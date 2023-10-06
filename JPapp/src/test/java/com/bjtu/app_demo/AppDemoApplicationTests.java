package com.bjtu.app_demo;

import com.bjtu.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppDemoApplicationTests {

    @Autowired
    UserDao userDao;

    @Test
    void contextLoads() {
    }

    @Test
    void insertTest(){

    }

}
