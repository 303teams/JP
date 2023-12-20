package com.bjtu.util;

import com.bjtu.dao.ContentDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileUtilsTest {

    @Autowired
    FileUtils fileUtils;
    @Autowired
    ContentDao contentDao;

    @Test
    void transToString() {
        Map<String,Object> map = contentDao.findContentByCTID(122);
        System.out.println(fileUtils.transToString((byte[])map.get("content"), (String)map.get("fileName"))
        );
    }
}