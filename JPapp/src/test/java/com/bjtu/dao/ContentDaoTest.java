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
        System.out.println(contentDao.findxCTById(3,"21301002"));
    }

    @Test
    void findCTById() {
        System.out.println(contentDao.findCTById(3));
    }

    @Test
    void insert() {
    }

    @Test
    void findCTByHId() {
    }

    @Test
    void setCTScore() {
    }


    @Test
    void findSnoByCID() {
    }

    @Test
    void findCTSByCID() {
        System.out.println(contentDao.findCTSByCID(2));
    }
}