package com.bjtu.controller;


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
    //        System.out.println(cno+" "+homeworkID+" "+user.getId());
        String name = file.getOriginalFilename();
    //        System.out.println("filename: "+name);

        content.setContent(file.getBytes())
                .setHomeworkID(homeworkID)
                .setFileName(name)
                .setSno(user.getId())
                .setCno(cno) ;

        contentService.addContent(content);
        // 添加对响应头的修改
        response.setHeader("Content-Disposition", "attachment;filename=" + name);
        byte[] bytes = file.getBytes();
        return RspObject.success("上传成功，当前thId：" , bytes);
    }

//    学生/老师 下载 学生的作业
    @AuthAccess
    @PostMapping("/downloadCT")
    public ResponseEntity<byte[]> downloadCT(@RequestParam Integer contentID) {
        System.out.println("hh"+ contentID);
        Content content1 = contentService.findById(contentID);

        System.out.println("hh"+ contentID);

        byte[] content = content1.getContent();
        String fileName = content1.getFileName();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}
