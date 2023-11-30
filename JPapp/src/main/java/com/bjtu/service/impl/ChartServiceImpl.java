package com.bjtu.service.impl;

import com.bjtu.dao.ContentDao;
import com.bjtu.dao.HomeworkDao;
import com.bjtu.exception.ServiceException;
import com.bjtu.pojo.Homework;
import com.bjtu.pojo.RspObject;
import com.bjtu.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("ChartService")
public class ChartServiceImpl implements ChartService {

    @Autowired
    ContentDao contentDao;

    public RspObject<Map<String,Object>> getScoreDistribution(Integer homeworkID){
        try{
            return RspObject.success("分数分布查询成功！",
                    contentDao.getScoreDistribution(homeworkID));
        }catch(Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }
}
