package com.bjtu.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseDaoTest {

    @Autowired
    CourseDao courseDao;

    @Test
    void findTHCourse() {
        System.out.println(courseDao.findSTCourse("21301001"));
    }

    @Test
    void findSTCourse() {
    }
}