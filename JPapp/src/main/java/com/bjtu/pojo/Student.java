package com.bjtu.pojo;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Data
public class Student extends User {

    private String sex;
    private Integer age;

    public Student() {
    }

    public Student(String id, String password, String name, String email, String sex, Integer age) {
        super(id, password, name, email);
        this.sex = sex;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + super.getId() +
                ", name='" + super.getName() + "\'" +
                "，sex='" + sex + "\'" +
                ",email='" + super.getEmail() + "\'" +
                ", age=" + age +
                "，password='" + super.getPassword() + "\'" +
                '}';
    }
}


