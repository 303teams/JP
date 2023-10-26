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
    private String email;


    public User(Integer id, String username, String password, String gender, String addr,String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.addr = addr;
        this.email = email;
    }

    public User(String username, String password, String gender, String addr,String email) {
        this(0,username,password,gender,addr,email);
    }
    public User(String username, String password) {
        this(0,username,password,"","","");
    }
    public String getEmail(){
        return this.email;
    }
}
