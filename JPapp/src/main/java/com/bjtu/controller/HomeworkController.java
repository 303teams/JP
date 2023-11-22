package com.bjtu.controller;


import cn.hutool.core.date.DateTime;
import com.bjtu.config.AuthAccess;
import com.bjtu.pojo.Homework;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.User;
import com.bjtu.service.HomeworkService;
import com.bjtu.service.StudentService;
import com.bjtu.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("homework")
public class HomeworkController {
    @Autowired
    HomeworkService homeworkService;

//    学生/老师 下载 作业
    @AuthAccess
    @PostMapping("/downloadHW")
    public ResponseEntity<byte[]> downloadHW(String homeworkId){
//        System.out.println("hh"+homeworkId);
        Homework homework = homeworkService.findHWById(homeworkId);

//        System.out.println("hh"+homeworkId);sno

        byte[] content = homework.getContent();
        String fileName = homework.getFileName();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

//    老师上传作业
    @AuthAccess
    @PostMapping("/uploadHW")
    public RspObject<Object> uploadHW(@RequestParam("file") MultipartFile file, @RequestParam String cno,String scoreDdl,String submitDdl) throws IOException {
        Homework homework = new Homework();
        String name = file.getOriginalFilename();

        User user = TokenUtils.getCurrentUser();
        homework.setContent(file.getBytes())
                .setTno(user.getId())
                .setFileName(name)
                .setCno(cno)
                .setSubmitDdl(new DateTime(submitDdl))
                .setScoreDdl(new DateTime(scoreDdl));
        homeworkService.addHomework(homework);
        return RspObject.success("上传成功，当前thId：" , homework);
    }
}
