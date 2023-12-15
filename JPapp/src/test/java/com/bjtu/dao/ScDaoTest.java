package com.bjtu.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScDaoTest {

    @Autowired
    ScDao scDao;

    @Test
    void findCoursesBySno() {
    }

    @Test
    void getFinalScoresBySno() {
        Map<String,Map<String,Object>> datas = scDao.getFinalScoresBySno("21301006");
        System.out.println(datas.get("1001").get("score"));
    }
}