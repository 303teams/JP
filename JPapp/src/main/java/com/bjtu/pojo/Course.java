package com.bjtu.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Course implements Serializable {

    private String cno;
    private String tno;
    private String cname;
    private String tname;
    public Course(){}

}
