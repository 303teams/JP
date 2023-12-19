package com.bjtu.util;

import com.bjtu.dao.HomeworkDao;
import com.bjtu.pojo.FileObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NLPUtilsTest {

    @Autowired
    NLPUtils nlpUtils;
    @Autowired
    FileUtils fileUtils;
    @Autowired
    HomeworkDao homeworkDao;

    @Test
    void similarityCheck() {
//        System.out.println(nlpUtils.similarityCheck("我是中国人","我是中国人"));
        FileObject fileObject1 = fileUtils.getTextFromDoc(homeworkDao.findHWById(654).getContent());
        FileObject fileObject2 = fileUtils.getTextFromDocx(homeworkDao.findHWById(649).getContent());
        System.out.println(nlpUtils.similarityCheck(fileObject1.getContent(),fileObject2.getContent()));
    }
}