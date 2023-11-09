package com.bjtu.service.impl;

import com.bjtu.JPappApplication;
import com.bjtu.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class StudentServiceImplTest {

    @Autowired
    StudentService studentService;

    @Test
    void modifyEmail() {
        System.out.println(studentService.modifyEmail("21301044@bjtu.edu.cn"));
    }

    @Test
    void modifyPassword() {
        System.out.println(studentService.modifyPassword("111"));
    }
}