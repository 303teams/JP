package com.bjtu.service.impl;

import com.bjtu.dao.HomeworkDao;
import com.bjtu.dao.StudentDao;
import com.bjtu.exception.ServiceException;
import com.bjtu.pojo.Homework;
import com.bjtu.pojo.RspObject;
import com.bjtu.service.HomeworkService;
import com.bjtu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;
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

    @Override
    public RspObject<Boolean> submitHomework(String id, String homeworkId, String cno, String url) {
        try {
//            homeworkDao.addShOne(id, homeworkId, cno, url);
            return RspObject.success("提交成功！", Boolean.TRUE);
        } catch (Exception e) {
            throw new ServiceException("提交失败！");
        }
    }

}
