package com.bjtu.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentDaoTest {

    @Autowired
    StudentDao studentDao;

    @Test
    void findByNum() {
        System.out.println(studentDao.findByNum("21301001"));
    }
}