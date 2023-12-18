package com.bjtu.service.impl;

import com.bjtu.service.ContentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContentServiceImplTest {

    @Autowired
    ContentService contentService;

    @Test
    void findCTByHIDSno() {
        System.out.println(contentService.findCTByHIDSno(638,"21301005"));
    }
}