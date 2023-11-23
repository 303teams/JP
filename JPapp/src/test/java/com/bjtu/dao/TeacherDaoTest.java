package com.bjtu.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeacherDaoTest {

    @Autowired
    TeacherDao teacherDao;

    @Test
    void findByNum() {
        System.out.println(teacherDao.findByNum("777"));
    }

    @Test
    void findHWbyCno() {
        System.out.println(teacherDao.findHWbyCno("1001"));
    }
}