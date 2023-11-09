package com.bjtu.pojo;


import cn.zhz.privacy.annotation.FieldEncrypt;
import lombok.Data;

import java.io.Serializable;

@Data
//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User implements Serializable {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @FieldEncrypt
    private String password;

    private String name;
    private String email;
    private String token;


    public User() {
    }

    public User(String id, String password, String name,String email) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
    }

}


