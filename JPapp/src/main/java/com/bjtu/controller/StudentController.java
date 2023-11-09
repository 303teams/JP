package com.bjtu.controller;

import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.Student;
import com.bjtu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("modifyEmail")
    public RspObject<String> modifyEmail(String email){
        return studentService.modifyEmail(email);
    }

    @PostMapping("modifyPassword")
    public RspObject<String> modifyPassword(String Password){
        return studentService.modifyPassword(Password);
    }

    @PostMapping("modifyInfo")
    public RspObject<String> modifyInfo(Student student){
        return studentService.modifyInfo(student);
    }


}
