package com.bjtu.pojo;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//@Entity
//@DiscriminatorValue("teacher")
@Data
public class Teacher extends User {
    private Integer age;
}
