package com.bjtu.controller;

import cn.hutool.core.io.FileUtil;
import com.bjtu.config.AuthAccess;
import com.bjtu.pojo.*;
import com.bjtu.service.ContentService;
import com.bjtu.service.HomeworkService;
import com.bjtu.service.StudentService;
import com.bjtu.service.TeacherService;
import com.bjtu.util.FileUtils;
import com.bjtu.util.TokenUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;
    @Autowired
    HomeworkService homeworkService;
    @Autowired
    ContentService contentService;


    @PostMapping("modifyInfo")
    public RspObject<String> modifyInfo(Teacher teacher){
        return teacherService.modifyInfo(teacher);
    }

//    教师查看自己的课程列表
    @AuthAccess
    @PostMapping("/findCourse")
    public RspObject<List<Map<String, Object>>> CourseList() {
        User user = TokenUtils.getCurrentUser();
        return teacherService.findCourse(user.getId());
    }

//    教师查看本课程布置的所有作业的列表
    @AuthAccess
    @PostMapping("/findHWbyCno")
    public RspObject<List<Homework>> findHWbyCno(@RequestParam String cno) {
        return teacherService.findHWbyCno(cno);
    }

//    教师点击某课程的某作业得到班级名单
    @AuthAccess
    @PostMapping("/findCTByHId")
    public RspObject<List<Content>> findCTByHId(@RequestParam Integer homeworkID){
        return teacherService.findCTByHId(homeworkID);
    }

//    教师修改学生分数
    @AuthAccess
    @PostMapping("/setCTScore")
    public RspObject<Boolean> setCTScore(@RequestParam Integer contentID,Integer score){
        return teacherService.setCTScore(contentID,score);
    }

    //教师查看学生申诉信息
    @AuthAccess
    @PostMapping("/findAppeal")
    public RspObject<List<Map<String, Object>>> findAppeal(){
        User user = TokenUtils.getCurrentUser();
        return teacherService.findAppeal(user.getId());
    }

    //教师点击申诉信息,改变staus值：0-1
    @AuthAccess
    @PostMapping("/ClickAppeal")
    public RspObject<Boolean> ClickAppeal(int contentID){

//        System.out.println("contentID: "+contentID);
        Appeal appeal = new Appeal();
        appeal = teacherService.findAPByID(contentID);

        int num =1;
        if(appeal.getStatus()==0){
//            System.out.println("contentID: "+contentID);
            return teacherService.setAP(contentID,num);
        }
        else  return RspObject.success("已处理！");
    }

    //    申诉后教师修改学生分数
    @AuthAccess
    @PostMapping("/changeCTScore")
    public RspObject<Boolean> changeCTScore(Integer contentID,Integer score){
        teacherService.setAP(contentID,2);
        return teacherService.setCTScore(contentID,score);
    }

    @AuthAccess
    @PostMapping("/findAPByAID")
    public RspObject<Map<String,Object>> findAPByAID(Integer appealID){
        return teacherService.findAPByAID(appealID);
    }

}
