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
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
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
        System.out.println("in1");
        return studentService.findCTsByCID(contentID);
    }



    //学生打分
    @PostMapping("score")
    public RspObject<Boolean> score(Integer contentID,Double score) {
        User user = TokenUtils.getCurrentUser();
        Score s= new Score();
        s.setSno(user.getId());
        s.setContentID(contentID);
        s.setScore(score);
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        s.setTime(formattedTime);
        return studentService.insertScore(s);
    }

    //发送申诉请求
    @PostMapping("sendAppeal")
    public RspObject<Boolean> sendAppeal(Integer contentID, String appealContent) {
        System.out.println("in2");
        Appeal appeal = new Appeal();
        appeal.setContentID(contentID);
        appeal.setAppealContent(appealContent);
        appeal.setStatus(0);

        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        appeal.setTime(formattedTime);
        return studentService.insertAppeal(appeal);
    }
}
