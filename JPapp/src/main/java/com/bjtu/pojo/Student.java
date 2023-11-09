package com.bjtu.pojo;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//@Entity
//@DiscriminatorValue("student")
@Data
public class Student extends User {

    private String sex;
    private Integer age;

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


