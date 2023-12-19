package com.bjtu.controller;

import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.User;
import com.bjtu.service.ChartService;
import com.bjtu.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("chart")
public class ChartController {

    @Autowired
    ChartService chartService;

    @PostMapping("/getScoreDistribution")
    public RspObject<Map<String,Object>> getScoreDistribution(Integer homeworkID){
        System.out.println("getScoreDistribution: "+homeworkID);
        return chartService.getScoreDistribution(homeworkID);
    }

    @PostMapping("/getAllRanking")
    public RspObject<List<Map<String, Object>>> getAllRanking(){
        User user = TokenUtils.getCurrentUser();
        return chartService.getAllRanking(user.getId());
    }


}
