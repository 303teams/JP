package com.bjtu.pojo;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//@Entity
//@DiscriminatorValue("student")
@Data
public class Student extends User {

    public String sex;
    public Integer age;

}


