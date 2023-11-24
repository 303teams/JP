package com.bjtu.dao;

import cn.hutool.core.date.DateTime;
import com.bjtu.pojo.Homework;
import com.bjtu.service.HomeworkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HomeworkDaoTest {

    @Autowired
    HomeworkService homeworkService;
    @Autowired
    HomeworkDao homeworkDao;

    @Test
    void insert() {
        homeworkDao.insert(new Homework()
                .setContent(null)
                .setCno("1001")
                .setFileName("123.txt")
//                .setScoreDdl("2001-1-10")
        );
    }
}