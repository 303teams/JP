package com.bjtu.controller;

import com.bjtu.pojo.*;
import com.bjtu.service.ContentService;
import com.bjtu.service.HomeworkService;
import com.bjtu.service.StudentService;
import com.bjtu.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.scanner.ScannerImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public RspObject<String> modifyInfo(Student student) {
        return studentService.modifyInfo(student);
    }

    // 学生的课程列表
    @PostMapping("/findCourse")
    public RspObject<List<Map<String, Object>>> CourseList() {
        User user = TokenUtils.getCurrentUser();
        return studentService.findCourse(user.getId());
    }

    //    学生查看自己某课程的作业列表
    @PostMapping("/findCTByCno")
    public RspObject<List<Homework>> findCTByCno(String cno) {
//        System.out.println(cno);
        User user = TokenUtils.getCurrentUser();
        return studentService.findHWbyCno(user.getId(), cno);
    }

    //    返回互评作业列表
    @PostMapping("findCTsByCID")
    public RspObject<List<Content>> findCTsByCID(Integer contentID) {
        return studentService.findCTsByCID(contentID);
    }

    //学生打分
    @PostMapping("score")
    public RspObject<Boolean> score(Integer contentID,Integer score,String content) {
        return studentService.score(contentID,score,content);
    }

    //发送申诉请求
    @PostMapping("sendAppeal")
    public RspObject<Boolean> sendAppeal(Integer contentID, String appealContent) {
        return studentService.handleAppeal(contentID, appealContent);
    }

    @PostMapping("findSCByCID")
    public RspObject<List<Score>> findSCByCID(Integer contentID){
        return studentService.findSCByCID(contentID);
    }



}
