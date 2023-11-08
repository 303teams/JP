package com.bjtu.pojo;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User implements Serializable {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String password;
    private String name;
    private String email;
    private String token;


    public User() {
    }

    public User(Integer id, String password, String name,String email) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
    }

}


