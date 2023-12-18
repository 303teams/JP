package com.bjtu.service.impl;

import com.bjtu.JPappApplication;
import com.bjtu.pojo.Student;
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
        System.out.println(studentService.modifyPassword("","111"));
    }

    @Test
    void insert() {
        System.out.println(studentService.insert(
                new Student("111","111","lzq","1065261339@qq.com","男",20)));
    }

    @Test
    void deleteOne() {
        System.out.println(studentService.deleteOne("666"));
    }

    @Test
    void changePassword() {
//        System.out.println(studentService.changePassword("21301001","21301001"));
        System.out.println(new Student("444","444","lzc","2461172547@qq.com","男",21).getPassword());
    }

    @Test
    void testChangePassword() {
        System.out.println(studentService.changePassword("21301001","21301001"));
    }

    @Test
    void findCTsByCID() {
        System.out.println(studentService.findCTsByCID(108));
    }

    @Test
    void handleAppeal() {
        System.out.println(studentService.handleAppeal(2,"我不满意"));
    }

    @Test
    void findHWbyCno() {
        System.out.println(studentService.findHWbyCno("21301001","1001"));
    }
}