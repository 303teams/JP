package com.bjtu.pojo;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Data
public class Student extends User {

    private String sex;
    private Integer age;
    private Integer exist;

    public Student() {
    }

    public Student(String id, String password, String name, String email, String sex, Integer age) {
        super(id, password, name, email);
        this.sex = sex;
        this.age = age;
    }

}


