package com.bjtu.service.impl;

import com.bjtu.dao.ContentDao;
import com.bjtu.dao.ScDao;
import com.bjtu.exception.ServiceException;
import com.bjtu.pojo.RspObject;
import com.bjtu.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

@Service("ChartService")
public class ChartServiceImpl implements ChartService {

    @Autowired
    ContentDao contentDao;

    @Autowired
    ScDao scDao;

    public RspObject<Map<String,Object>> getScoreDistribution(Integer homeworkID){
        try{
            return RspObject.success("分数分布查询成功！",
                    contentDao.getScoreDistribution(homeworkID));
        }catch(Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }

//    返回一个学生每项课程的的每项作业的成绩排名百分比数据
    @Override
    public RspObject<List<Map<String, Object>>> getAllRanking(String id) {
        List data = new ArrayList<Map<String,Object>>();
        try{
//            获取学生课程列表
            List<Integer> courseList = scDao.findCoursesBySno(id);
            for(Integer courseID:courseList){
                data.add(contentDao.getRankingByCourse(courseID,id));
            }
            return RspObject.success("学生成绩查询成功！",data);
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }
}
