package com.bjtu.pojo;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//@Entity
//@DiscriminatorValue("teacher")
@Data
public class Teacher extends User {
    private Integer age;
    private String sex;
    private Integer exist;

    public Teacher() {
    }

    public Teacher(String id, String password, String name, String email, String sex, Integer age) {
        super(id, password, name, email);
        this.sex = sex;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + super.getId() +
                ", name='" + super.getName() + "\'" +
                "，sex='" + sex + "\'" +
                ",email='" + super.getEmail() + "\'" +
                ", age=" + age +
                "，password='" + super.getPassword() + "\'" +
                '}';
    }
}
