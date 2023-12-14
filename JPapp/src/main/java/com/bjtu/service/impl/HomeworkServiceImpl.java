package com.bjtu.service.impl;

import com.bjtu.dao.HomeworkDao;
import com.bjtu.dao.StudentDao;
import com.bjtu.exception.ServiceException;
import com.bjtu.pojo.Homework;
import com.bjtu.pojo.RspObject;
import com.bjtu.service.HomeworkService;
import com.bjtu.service.StudentService;
import com.bjtu.task.ScheduledTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;
import java.util.List;

@Service("HomeworkService")
public class HomeworkServiceImpl implements HomeworkService {

    @Autowired
    HomeworkDao homeworkDao;
    @Autowired
    ScheduledTask scheduledTask;

    @Override
    public RspObject<List<Homework>> findAll(){
        return RspObject.success("查询成功！",homeworkDao.findAll());
    }

    public Homework findHWById(Integer id) {
        return homeworkDao.findHWById(id);
    }

    @Override
    public void addHomework(Homework homework) {
//        新添作业，并将该作业添加到扫描线程内
        homeworkDao.insert(homework);
        scheduledTask.addHomework(homework.getHomeworkID());
    }

    @Override
    public RspObject<Boolean> deleteByHId(Integer homeworkID) {
        try{
            homeworkDao.deleteByHId(homeworkID);
            return RspObject.success("删除成功",Boolean.TRUE);
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }

    @Override
    public RspObject<Boolean> setAnswer(Integer homeworkID, byte[] answer,String Afilename) {
        try {
            homeworkDao.setAnswer(homeworkID,answer,Afilename);
            return RspObject.success("添加成功",Boolean.TRUE);
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }

    @Override
    public RspObject<Boolean> alterScoreDdlByHID(Integer homeworkID, String scoreDdl) {
        try{
            homeworkDao.updateScoreDdl(homeworkID,scoreDdl);
            scheduledTask.alterScoreDdl(homeworkID,scoreDdl);
            return RspObject.success("修改成功",Boolean.TRUE);
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }

    @Override
    public RspObject<Boolean> alterSubmitDdlByHID(Integer homeworkID, String submitDdl) {
        try{
            homeworkDao.updateSubmitDdl(homeworkID,submitDdl);
            scheduledTask.alterSubmitDdl(homeworkID,submitDdl);
            return RspObject.success("修改成功",Boolean.TRUE);
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }
}
