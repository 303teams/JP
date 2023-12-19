package com.bjtu.dao;
import com.bjtu.pojo.Homework;
import com.bjtu.service.HomeworkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@SpringBootTest
class HomeworkDaoTest {

    @Autowired
    HomeworkService homeworkService;
    @Autowired
    HomeworkDao homeworkDao;

    @Test
    void insert() {
        Homework homework = new Homework();
        homeworkDao.insert(homework);
        System.out.println(homework.getHomeworkID());
    }


    @Test
    void findHWById() {
        System.out.println(homeworkDao.findHWById(573));
    }

    @Test
    void findHWbyCno() {
        System.out.println(homeworkDao.findHWbyCno("21301001","1001"));
    }

    @Test
    void findSimpleAll() {
        System.out.println(homeworkDao.findSimpleAll());
    }

    @Test
    void findHWInfoByCno() {
        System.out.println(homeworkDao.findHWInfoByCno("1001"));
    }
}