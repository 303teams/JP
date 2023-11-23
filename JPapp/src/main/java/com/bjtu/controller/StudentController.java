package com.bjtu.controller;

import cn.hutool.json.JSONObject;
import com.bjtu.config.AuthAccess;
import com.bjtu.pojo.*;
import com.bjtu.service.ContentService;
import com.bjtu.service.HomeworkService;
import com.bjtu.service.StudentService;
import com.bjtu.util.FileUtils;
import com.bjtu.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    StudentService studentService;
    @Autowired
    HomeworkService homeworkService;
    @Autowired
    ContentService contentService;

    @PostMapping("modifyInfo")
    public RspObject<String> modifyInfo(Student student){
        return studentService.modifyInfo(student);
    }

    // 学生的课程列表
    @AuthAccess
    @PostMapping("/findCourse")
    public RspObject<List<Map<String, Object>>> CourseList() {
        User user = TokenUtils.getCurrentUser();
        return studentService.findCourse(user.getId());
    }

//    学生查看自己某课程的作业列表
    @AuthAccess
    @PostMapping("/findCTByCno")
    public RspObject<List<Homework>> findCTByCno(String cno) {
//        System.out.println(cno);
        User user = TokenUtils.getCurrentUser();
        return studentService.findHWbyCno(user.getId(),cno);
    }

}
