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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("homework")
public class HomeworkController {
    @Autowired
    HomeworkService homeworkService;

//    学生/老师 下载 作业
    @AuthAccess
    @PostMapping("/downloadHW")
    public ResponseEntity<byte[]> downloadHW(String homeworkId){
        System.out.println("hh"+homeworkId);
        Homework homework = homeworkService.findHWById(homeworkId);

//        System.out.println("hh"+homeworkId);sno

        byte[] content = homework.getContent();
        String fileName = homework.getFileName();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }


//    @AuthAccess
        @PostMapping("/findById")
        public RspObject<List<Homework>> ByIdList(String cno) {
            System.out.println(cno);
            User user = TokenUtils.getCurrentUser();
            return homeworkService.findById(user.getId(),cno);
        }

    @PostMapping("/findByTeaId")
    public RspObject<List<Homework>> ByTeaIdList(String cno) {
        System.out.println(cno);
        User user = TokenUtils.getCurrentUser();
        return homeworkService.findByTeaId(user.getId(),cno);
    }

//    老师上传作业
    @AuthAccess
    @PostMapping("/uploadHW")
    public RspObject<Object> uploadHW(@RequestParam("file") MultipartFile file, @RequestParam String cno,String scoreDdl,String submitDdl,String HWName) throws IOException {
        Homework homework = new Homework();
        String name = file.getOriginalFilename();

        User user = TokenUtils.getCurrentUser();
        homework.setContent(file.getBytes())
                .setTno(user.getId())
                .setFileName(name)
                .setCno(cno)
                .setSubmitDdl(submitDdl)
                .setScoreDdl(scoreDdl)
                .setName(HWName);
        homeworkService.addHomework(homework);
        return RspObject.success("上传成功，当前thId：" , homework);
    }

}
