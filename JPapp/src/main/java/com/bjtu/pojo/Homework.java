package com.bjtu.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class Homework {

    private String homeworkID;
    private String cno;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date submitDdl;
    private String content;
    private String tno;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date scoreDdl;
    private String teacherName;
    private String courseName;


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


