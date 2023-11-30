package com.bjtu.service.impl;

import com.bjtu.dao.ContentDao;
import com.bjtu.service.ChartService;
import com.bjtu.service.ContentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChartServiceImplTest {

    @Autowired
    ChartService chartService;

    @Test
    void getScoreDistribution() {
        System.out.println(chartService.getScoreDistribution(21));
    }
}