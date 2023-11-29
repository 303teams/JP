package com.bjtu.pojo;


import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
public class Homework implements Serializable {

    private Integer homeworkID;
    private String cno;
    private String name;
    private String tno;
    private String fileName;
    private byte[] content;
    public Homework(Integer homeworkID,String cno,String name,byte[] content,String tno){

        this.homeworkID = homeworkID;
        this.cno = cno;
        this.name = name;
        this.content = content;
        this.tno = tno;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String submitDdl;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String scoreDdl;


    private String teacherName;
    private String contentID;
    private String courseName;

//    总人数和提交人数
    private Integer totalAmount;
    private Integer submitAmount;

    private Integer score;

    public Homework() {
    }



//    public Homework(String id, String cno, String hname, String tno, Date ddl) {
//        super(id, cno, hname, tno,ddl);
//    }

//    @Override
//    public String toString() {
//        return "Student{" +
//                "id=" + super.getId() +
//                ", name='" + super.getName() + "\'" +
//                "，sex='" + sex + "\'" +
//                ",email='" + super.getEmail() + "\'" +
//                ", age=" + age +
//                "，password='" + super.getPassword() + "\'" +
//                '}';
//    }
}


