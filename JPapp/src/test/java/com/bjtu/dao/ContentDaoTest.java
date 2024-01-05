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
        System.out.println(contentDao.findxCTById(146,"21301008"));
    }

    @Test
    void findCTById() {
        System.out.println(contentDao.findCTById(1));
    }

    @Test
    void insert() {
    }

    @Test
    void findCTByHId() {
        System.out.println(contentDao.findCTByHId(602));
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

    @Test
    void getScoreDistribution() {
        System.out.println(contentDao.getScoreDistribution(21));
    }

    @Test
    void deliverTask() {
        contentDao.deliverTask(1);
    }

    @Test
    void findCTscoreByHId() {
        System.out.println(contentDao.findCTscoreByHId(603));
    }

    @Test
    void findSimilarCTs() {
        System.out.println(contentDao.findSimilarCTs(29));
    }
}