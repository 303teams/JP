package com.bjtu.controller;

import cn.hutool.core.io.FileUtil;
import com.bjtu.config.AuthAccess;
import com.bjtu.pojo.*;
import com.bjtu.service.HomeworkService;
import com.bjtu.service.StudentService;
import com.bjtu.service.TeacherService;
import com.bjtu.util.FileUtils;
import com.bjtu.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.util.Date;

@RestController
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;
    @Autowired
    HomeworkService homeworkService;
    @Resource
    FileUtils fileUtils;
//    @PostMapping("modifyEmail")
//    public RspObject<String> modifyEmail(String email){
//        return teacherService.modifyEmail(email);
//    }
//
//    @PostMapping("modifyPassword")
//    public RspObject<String> modifyPassword(String newPassword,String oldPassword){
//        return teacherService.modifyPassword(newPassword,oldPassword);
//    }

    @PostMapping("modifyInfo")
    public RspObject<String> modifyInfo(Teacher teacher){
        System.out.println("modifyInfo: "+teacher);
        return teacherService.modifyInfo(teacher);
    }

    @AuthAccess
    @PostMapping("/uploadHW")
    public RspObject<Object> uploadHW(@RequestParam("file") MultipartFile file, @RequestParam String Id, @RequestParam String cno) throws IOException {
        Homework homework = new Homework();
        String name = file.getOriginalFilename();


        User user = TokenUtils.getCurrentUser();
        homework.setContent(file.getBytes())
                .setHomeworkID(Id)
                .setTno(user.getId())
                .setName(name)
                .setCno(cno);
// .setSubmitDdl(submit_ddl)
// .setScoreDdl(score_ddl)
        homeworkService.addHomework(homework);
        return RspObject.success("上传成功，当前thId：" + Id , homework);
    }

    @AuthAccess
    @PostMapping("/downloadHW")
    public  RspObject<Object> downloadHW(String homeworkID, HttpServletResponse response){
        Homework homework = homeworkService.findHomeworkByThId(homeworkID);
        System.out.println(homework.getContent());
//        if(fileUtils.downloadFile(homework.getContent(),homework.getName(), response)){
//            System.out.println("下载成功123");
//            return RspObject.success("成功下载", "1");
//        }else{
//            return RspObject.fail("下载失败", homework);
//        }
        return RspObject.success("成功下载", homework);

    }


}
