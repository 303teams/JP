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
    @Resource
    FileUtils fileUtils;


    @PostMapping("modifyInfo")
    public RspObject<String> modifyInfo(Teacher teacher){
        System.out.println("modifyInfo: "+teacher);
        return teacherService.modifyInfo(teacher);
    }

//    教师查看自己的课程列表
    @AuthAccess
<<<<<<< HEAD
    @PostMapping("/uploadHW")
    public RspObject<Object> uploadHW(@RequestParam("file") MultipartFile file, @RequestParam String cno) throws IOException {
        Homework homework = new Homework();
        String name = file.getOriginalFilename();

        User user = TokenUtils.getCurrentUser();
        homework.setContent(file.getBytes())
                .setTno(user.getId())
                .setName(name)
                .setCno(cno);
//                .setSubmitDdl(submit_ddl)
//                .setScoreDdl(score_ddl)
        homeworkService.addHomework(homework);
        return RspObject.success("上传成功，当前thId："  , homework);
=======
    @PostMapping("/findCourse")
    public RspObject<List<Map<String, Object>>> CourseList() {
        User user = TokenUtils.getCurrentUser();
        return teacherService.findCourse(user.getId());
>>>>>>> lzc
    }

//    教师查看本课程所有学生提交的作业列表
    @AuthAccess
    @PostMapping("/findHWbyCno")
    public RspObject<List<Homework>> findHWbyCno(@RequestParam String cno) {
        User user = TokenUtils.getCurrentUser();
        return homeworkService.findById(user.getId(),cno);
    }

}
