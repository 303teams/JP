package com.bjtu.service;

import com.bjtu.pojo.RspObject;

import java.util.List;
import java.util.Map;

public interface ChartService {

//    得出某项作业的成绩分布
    public RspObject<Map<String,Object>> getScoreDistribution(Integer homeworkID);

//    获得一个学生的所有课程的成绩
    public RspObject<List<Map<String,Object>>> getAllRanking(String id);

}
