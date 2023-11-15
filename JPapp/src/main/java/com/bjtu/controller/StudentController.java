package com.bjtu.controller;

import com.bjtu.config.AuthAccess;
import com.bjtu.pojo.*;
import com.bjtu.service.StudentService;
import com.bjtu.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    StudentService studentService;

//    @PostMapping("modifyEmail")
//    public RspObject<String> modifyEmail(String email){
//        return studentService.modifyEmail(email);
//    }
//
//    @PostMapping("modifyPassword")
//    public RspObject<String> modifyPassword(String newPassword,String oldPassword){
//        return studentService.modifyPassword(newPassword,oldPassword);
//    }

    @PostMapping("modifyInfo")
    public RspObject<String> modifyInfo(Student student){
        return studentService.modifyInfo(student);
    }

    @AuthAccess
    @PostMapping("/findCourse")
    public RspObject<List<Course>> CourseList(String uu) {
        //User user = TokenUtils.getCurrentUser();
        return studentService.findCourse(uu);
    }

}
