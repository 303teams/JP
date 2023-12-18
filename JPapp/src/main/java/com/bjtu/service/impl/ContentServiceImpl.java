package com.bjtu.service.impl;

import com.bjtu.dao.HomeworkDao;
import com.bjtu.exception.ServiceException;
import com.bjtu.pojo.Content;
import com.bjtu.pojo.Homework;
import com.bjtu.pojo.RspObject;
import com.bjtu.dao.ContentDao;
import com.bjtu.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("ContentService")
public class ContentServiceImpl implements ContentService {

    @Autowired
    ContentDao contentDao;
    @Override
    public Content findById(Integer id) {
        return contentDao.findCTById(id);
    }

    @Override
    public void addContent(Content content) {
        contentDao.insert(content);
    }

    @Override
    public Content findCTByHIDSno(Integer homeworkID, String sno) {
        return contentDao.findCTByHIDSno(homeworkID,sno);
    }

    @Override
    public RspObject<Object> alterContent(Content content) {
        try{
            contentDao.alterContent(content);
            return RspObject.success("修改成功");
        }catch (Exception e){
            throw new ServiceException(500, e.getMessage());
        }


    }
}