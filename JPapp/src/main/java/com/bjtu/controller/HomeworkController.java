package com.bjtu.controller;


import com.bjtu.config.AuthAccess;
import com.bjtu.pojo.Homework;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.User;
import com.bjtu.service.HomeworkService;
import com.bjtu.service.StudentService;
import com.bjtu.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("homework")
public class HomeworkController {
    @Autowired
    HomeworkService homeworkService;

    @AuthAccess
    @PostMapping("/findAll")
    public RspObject<List<Homework>> findAllList() {
        return homeworkService.findAll();
    }

//    @AuthAccess
    @PostMapping("/findById")
    public RspObject<List<Homework>> ByIdList() {
        User user = TokenUtils.getCurrentUser();
        return homeworkService.findById(user.getId());
    }

}
