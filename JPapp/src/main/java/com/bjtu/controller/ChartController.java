package com.bjtu.controller;

import com.bjtu.dao.HomeworkDao;
import com.bjtu.pojo.RspObject;
import com.bjtu.service.ChartService;
import com.bjtu.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("chart")
public class ChartController {

    @Autowired
    ChartService chartService;

    @PostMapping("getScoreDistribution")
    public RspObject<Map<String,Object>> getScoreDistribution(Integer homeworkID){
        System.out.println("getScoreDistribution: "+homeworkID);
        return chartService.getScoreDistribution(homeworkID);
    }

}
