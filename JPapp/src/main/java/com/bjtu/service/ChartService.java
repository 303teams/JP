package com.bjtu.service;

import com.bjtu.pojo.RspObject;

import java.util.Map;

public interface ChartService {

//    得出某项作业的成绩分布
    public RspObject<Map<String,Object>> getScoreDistribution(Integer homeworkID);

}
