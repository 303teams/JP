package com.bjtu.service.impl;

import com.bjtu.dao.HomeworkDao;
import com.bjtu.dao.StudentDao;
import com.bjtu.pojo.Homework;
import com.bjtu.pojo.RspObject;
import com.bjtu.service.HomeworkService;
import com.bjtu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("HomeworkService")
public class HomeworkServiceImpl implements HomeworkService {

    @Autowired
    HomeworkDao homeworkDao;
    @Override
    public RspObject<List<Homework>> findAll(){
        return RspObject.success("查询成功！",homeworkDao.findAll());
    }

    @Override
    public RspObject<List<Homework>> findById(String id,String cno){
        return RspObject.success("查询成功！",homeworkDao.findById(id,cno));
    }

}
