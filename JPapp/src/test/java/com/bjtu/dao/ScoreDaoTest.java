package com.bjtu.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScoreDaoTest {

    @Autowired
    ScoreDao scoreDao;

    @Test
    void setInvalidByCID() {
        scoreDao.setInvalidByCID(141,143);
    }
}