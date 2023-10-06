package com.bjtu.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private Integer id;
    private String username;
    private String password;
    private String gender;
    private String addr;

    public User(Integer id, String username, String password, String gender, String addr) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.addr = addr;
    }

    public User(String username, String password, String gender, String addr) {
        this(0,username,password,gender,addr);
    }
    public User(String username, String password) {
        this(0,username,password,"","");
    }
}
