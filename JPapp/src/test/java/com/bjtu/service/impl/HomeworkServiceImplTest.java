package com.bjtu.service.impl;

import cn.hutool.core.date.DateTime;
import com.bjtu.pojo.Homework;
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
    void addHomework() {
        homeworkService.addHomework(
                new Homework()
                        .setContent(null)
                        .setCno("1001")
                        .setFileName("123.txt")
//                        .setScoreDdl("2001-1-10")
        );
    }

//    @Test
//    void submitHomework() {
//        System.out.println(homeworkService.submitHomework("21301001","1","1001","www.baidu.com"));
//    }
}