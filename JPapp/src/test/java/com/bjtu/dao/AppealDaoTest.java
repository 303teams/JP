package com.bjtu.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppealDaoTest {

    @Autowired
    AppealDao appealDao;

    @Test
    void findAPByAID() {
        System.out.println(appealDao.findAPByAID(1));
    }
}