package com.bjtu.controller;

import com.bjtu.config.AuthAccess;
import com.bjtu.pojo.*;
import com.bjtu.service.ContentService;
import com.bjtu.service.HomeworkService;
import com.bjtu.service.StudentService;
import com.bjtu.util.FileUtils;
import com.bjtu.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    StudentService studentService;
    HomeworkService homeworkService;

    ContentService contentService;
    @Resource
    FileUtils fileUtils;

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
    public RspObject<List<Map<String, Object>>> CourseList() {
        User user = TokenUtils.getCurrentUser();
        return studentService.findCourse(user.getId());
    }

    @AuthAccess
    @GetMapping("/downloadHW/{homeworkID}")
    public  RspObject<Object> downloadHW(@PathVariable String homeworkID, HttpServletResponse response){
        Homework homework = homeworkService.findHomeworkByThId(homeworkID);
        if(fileUtils.downloadFile(homework.getContent(), homework.getName(), response))
            return RspObject.success("成功下载", homework);
        else return RspObject.fail("下载失败", homework);
    }
    @AuthAccess
    @PostMapping("/uploadCT")
    public RspObject<Object> uploadCT(MultipartFile file, String Id) throws IOException {
        Content content = new Content();
        User user = TokenUtils.getCurrentUser();
        System.out.println("hh");
        content.setContent(file.getBytes())
                .setContntID(Id)
                .setSno(user.getId());
        contentService.addContent(content);
        return RspObject.success("上传成功，当前thId：" + Id , content);
    }

    @AuthAccess
    @GetMapping("/downloadCT/{contentID}")
    public  RspObject<Object> downloadCT(@PathVariable String contentID, HttpServletResponse response){
        Content content = contentService.findById(contentID);
        if(fileUtils.downloadFile(content.getContent(), "homework_9", response))
            return RspObject.success("成功下载学生作业", content);
        else return RspObject.fail("学生下载失败", content);
    }

}
