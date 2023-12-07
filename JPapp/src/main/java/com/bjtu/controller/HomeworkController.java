package com.bjtu.controller;


import cn.hutool.core.date.DateTime;
import com.bjtu.config.AuthAccess;
import com.bjtu.pojo.Content;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@RestController
@RequestMapping("homework")
public class HomeworkController {
    @Autowired
    HomeworkService homeworkService;

//    学生/老师 下载 作业
    @AuthAccess
    @PostMapping("/downloadHW")
    public ResponseEntity<byte[]> downloadHW(Integer homeworkId) throws UnsupportedEncodingException {
        System.out.println("hh"+homeworkId);

        Homework homework = homeworkService.findHWById(homeworkId);

        byte[] content = homework.getContent();
        String fileName = homework.getFileName();
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8");

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", encodedFileName);
        headers.set("Content-Type", "application/octet-stream; charset=UTF-8");

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }


//    老师上传作业
    @AuthAccess
    @PostMapping("/uploadHW")
    public RspObject<Object> uploadHW(@RequestParam("file") MultipartFile file, @RequestParam String cno,String scoreDdl,String submitDdl,String HWName) throws IOException, ParseException {
        Homework homework = new Homework();
        String name = file.getOriginalFilename();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        User user = TokenUtils.getCurrentUser();
        homework.setContent(file.getBytes())
                .setTno(user.getId())
                .setFileName(name)
                .setCno(cno)
                .setSubmitDdl(new Timestamp(dateFormat.parse(submitDdl).getTime()))
                .setScoreDdl(new Timestamp(dateFormat.parse(scoreDdl).getTime()))
                .setName(HWName);
        homeworkService.addHomework(homework);
        return RspObject.success("上传成功，当前thId：" , homework);
    }

    //    教师上传正确答案
    @AuthAccess
    @PostMapping("/setAnswer")
    public RspObject<Boolean> setAnswer(@RequestParam("file") MultipartFile file,Integer homeworkID) throws IOException {
        String name = file.getOriginalFilename();
//        System.out.println("name"+name);
        return homeworkService.setAnswer(homeworkID,file.getBytes(),name);
    }

    @AuthAccess
    @PostMapping("/downloadAns")
    public ResponseEntity<byte[]> downloadAns(@RequestParam Integer homeworkID) throws UnsupportedEncodingException {
        Homework homework = homeworkService.findHWById(homeworkID);
        byte[] answer = homework.getAnswer();
        String fileName = homework.getAfilename();

        String encodedFileName = URLEncoder.encode(fileName, "UTF-8");

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", encodedFileName);
        headers.set("Content-Type","application/octet-stream; charset=UTF-8");

        return new ResponseEntity<>(answer, headers, HttpStatus.OK);
    }

}
