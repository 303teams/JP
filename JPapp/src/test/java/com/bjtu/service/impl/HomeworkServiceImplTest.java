package com.bjtu.service.impl;

import com.bjtu.service.HomeworkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HomeworkServiceImplTest {

    @Autowired
    HomeworkService homeworkService;

    @Test
    void submitHomework() {
        System.out.println(homeworkService.submitHomework("21301001","1","1001","www.baidu.com"));
    }
}