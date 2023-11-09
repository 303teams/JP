package com.bjtu.controller;

import com.bjtu.pojo.RspObject;
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
    public RspObject<String> modifyEmail(Integer id, String email){
        return studentService.modifyEmail(id,email);
    }


}
