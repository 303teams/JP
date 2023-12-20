package com.bjtu.util;

import com.bjtu.dao.HomeworkDao;
import com.bjtu.pojo.Homework;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileAcountUtilsTest {

    @Autowired
    HomeworkDao homeworkDao;
    @Autowired
    FileUtils fileUtils;

    @Test
    void readTextFile() {
    }

    @Test
    void readTextFileByte() {
    }

    @Test
    void getTextFromDocx() {

    }

    @Test
    void getTextFromDoc() {

    }

    @Test
    void getTextFromPDF() {

    }

    @Test
    void getTextFromPPT() {

    }

    @Test
    void getTextFromPPTX() {

    }
}