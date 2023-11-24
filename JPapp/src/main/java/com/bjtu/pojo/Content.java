package com.bjtu.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Accessors(chain = true)
public class Content {

    private Integer contentID;
    private String cno;
    private String sno;
    private Integer homeworkID;
    private byte[] content;
    private String fileName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String submitTime;

    private Integer score;
    private Integer content1;
    private Integer content2;
    private Integer content3;
    private Integer content4;
    private Integer content5;
    private Integer content6;

//    学生姓名
    private String sname;

    private String comment;


    public Content(){

    }
}
