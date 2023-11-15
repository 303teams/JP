package com.bjtu.pojo;


import lombok.Data;

import java.util.Date;


@Data
public class Homework {

    private String id;
    private String cno;
    private String hname;
    private String content;
    private String ddl;



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


