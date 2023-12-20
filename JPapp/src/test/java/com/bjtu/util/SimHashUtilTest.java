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
        Map<String,Object> map = contentDao.findContentByCTID(125);
        String ct1 = fileUtils.transToString((byte[])map.get("content"),(String)map.get("fileName"));
        System.out.println(ct1);
        String ct2 = "";
        SimHashUtil content1 = new SimHashUtil(ct1,64);
        SimHashUtil content2 = new SimHashUtil(ct2,64);
        System.out.println(content1.getSimilar(content2));
    }
}