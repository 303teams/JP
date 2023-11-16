package com.bjtu.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Blob;
import java.util.Date;


@Data
@Accessors(chain = true)
public class Homework {

    private String homeworkID;
    private String cno;
    private String name;
    private String tno;
    private byte[] content;
    public Homework(String homeworkID,String cno,String name,byte[] content,String tno){

        this.homeworkID = homeworkID;
        this.cno = cno;
        this.name = name;
        this.content = content;
        this.tno = tno;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date submitDdl;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
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


