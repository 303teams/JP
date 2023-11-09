package com.bjtu.service.impl;

import com.bjtu.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeacherServiceImplTest {

    @Autowired
    TeacherService teacherService;

    @Test
    void modifyEmail() {
        System.out.println(teacherService.modifyEmail("21301040@bjtu.edu.cn"));
    }

    @Test
    void modifyPassword() {
        System.out.println(teacherService.modifyPassword("","333"));
    }
}