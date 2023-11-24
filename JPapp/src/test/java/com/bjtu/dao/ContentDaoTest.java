package com.bjtu.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContentDaoTest {

    @Autowired
    ContentDao contentDao;

    @Test
    void findxCTById() {
        System.out.println(contentDao.findxCTById(2,"21301001"));
    }
}