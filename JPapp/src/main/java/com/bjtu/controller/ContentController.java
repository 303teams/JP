package com.bjtu.controller;


import cn.hutool.core.date.DateTime;
import com.bjtu.config.AuthAccess;
import com.bjtu.pojo.Content;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.User;
import com.bjtu.service.ContentService;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("content")
public class ContentController {

    @Autowired
    ContentService contentService;

//    学生上传作业
    @AuthAccess
    @PostMapping("/uploadCT")
    public RspObject<Object> uploadCT(@RequestParam("file") MultipartFile file,
                                      @RequestParam String cno,
                                      @RequestParam Integer homeworkID,
                                      HttpServletResponse response) throws IOException {
        Content content = new Content();
        User user = TokenUtils.getCurrentUser();
        String name = file.getOriginalFilename();

        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        content.setContent(file.getBytes())
                .setHomeworkID(homeworkID)
                .setFileName(name)
                .setSno(user.getId())
                .setCno(cno)
                .setSubmitTime(formattedTime);

        contentService.addContent(content);
        // 添加对响应头的修改
        response.setHeader("Content-Disposition", "attachment;filename=" + name);
        byte[] bytes = file.getBytes();
        return RspObject.success("上传成功，当前thId：" , bytes);
    }

//    学生/老师 下载 学生的作业
    @AuthAccess
    @PostMapping("/downloadCT")
    public ResponseEntity<byte[]> downloadCT(@RequestParam Integer contentID) throws UnsupportedEncodingException {
//        System.out.println("hh"+ contentID);
        Content content1 = contentService.findById(contentID);

//        System.out.println("hh"+ contentID);

        byte[] content = content1.getContent();
        String fileName = content1.getFileName();
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", encodedFileName);
        headers.set("Content-Type","application/octet-stream; charset=UTF-8");

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}
