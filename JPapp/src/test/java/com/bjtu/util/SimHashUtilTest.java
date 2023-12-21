package com.bjtu.util;

import com.bjtu.dao.ContentDao;
import com.bjtu.dao.HomeworkDao;
import com.bjtu.pojo.Content;
import com.bjtu.pojo.Homework;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SimHashUtilTest {

    @Autowired
    HomeworkDao homeworkDao;

    @Autowired
    FileUtils fileUtils;
    @Autowired
    ContentDao contentDao;

    @Test
    void getSimilar() {
        Homework homework1 = homeworkDao.findHWById(668);
        Homework homework2 = homeworkDao.findHWById(669);
        String ct1 = fileUtils.transToString(homework1.getContent(),homework1.getFileName());
        String ct2 = fileUtils.transToString(homework2.getContent(),homework2.getFileName());;
        SimHashUtil content1 = new SimHashUtil(ct1,64);
        SimHashUtil content2 = new SimHashUtil(ct2,64);
        System.out.println(content1.getSimilar(content2));
    }
}