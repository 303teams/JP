package com.bjtu.controller;

import com.bjtu.dao.AdminDao;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.Student;
import com.bjtu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    AdminService adminService;

//    @PostMapping("modifyEmail")
//    public RspObject<String> modifyEmail(String email){
//        return adminService.modifyEmail(email);
//    }

//    @PostMapping("modifyPassword")
//    public RspObject<String> modifyPassword(String password){
//        return adminService.modifyPassword(password);
//    }


}
