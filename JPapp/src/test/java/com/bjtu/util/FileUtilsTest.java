package com.bjtu.util;

import com.bjtu.dao.HomeworkDao;
import com.bjtu.pojo.Homework;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileUtilsTest {

    @Autowired
    HomeworkDao homeworkDao;
    @Autowired
    FileUtils fileUtils;

    @Test
    void readTextFile() {
    }

    @Test
    void readTextFileByte() {
        System.out.println(fileUtils.readTextFileByte(homeworkDao.findHWById(645).getContent()));
    }

    @Test
    void getTextFromDocx() {
        System.out.println(fileUtils.getTextFromDocx(homeworkDao.findHWById(649).getContent()));
    }

    @Test
    void getTextFromDoc() {
        System.out.println(fileUtils.getTextFromDoc(homeworkDao.findHWById(650).getContent()));
    }

    @Test
    void getTextFromPDF() {
        System.out.println(fileUtils.getTextFromPDF(homeworkDao.findHWById(651).getContent()));
    }

    @Test
    void getTextFromPPT() {
        System.out.println(fileUtils.getTextFromPPT(homeworkDao.findHWById(653).getContent()));
    }

    @Test
    void getTextFromPPTX() {
        System.out.println(fileUtils.getTextFromPPTX(homeworkDao.findHWById(652).getContent()));
    }
}