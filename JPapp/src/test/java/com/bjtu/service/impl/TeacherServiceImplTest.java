package com.bjtu.service.impl;

import com.bjtu.pojo.Teacher;
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
        System.out.println(teacherService.changePassword("777","777"));
    }

    @Test
    void insert(){
        System.out.println(teacherService.insert(new Teacher("222","222","lzq","12345","男",35)));
    }

    @Test
    void findCourse() {
        System.out.println(teacherService.findCourse("21001001"));
    }

    @Test
    void findCTByHId() {
        System.out.println(teacherService.findCTByHId(1));
    }

    @Test
    void setCTWeightedScore() {
        System.out.println(teacherService.setCTWeightedScore(266,100));
    }
}