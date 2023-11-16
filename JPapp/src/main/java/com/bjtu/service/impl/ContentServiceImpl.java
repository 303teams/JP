package com.bjtu.service.impl;

import com.bjtu.dao.HomeworkDao;
import com.bjtu.pojo.Content;
import com.bjtu.pojo.Homework;
import com.bjtu.pojo.RspObject;
import com.bjtu.dao.ContentDao;
import com.bjtu.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ContentService")
public class ContentServiceImpl implements ContentService {

    @Autowired
    ContentDao contentDao;
    @Override
    public Content findById(String id) {
        return contentDao.findContentById(id);
    }

    @Override
    public void addContent(Content content) {
        contentDao.insert(content);
    }
}