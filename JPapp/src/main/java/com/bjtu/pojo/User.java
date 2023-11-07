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
<<<<<<< HEAD
    private String gender;
    private String addr;
    private String email;


    public User(Integer id, String username, String password, String gender, String addr,String email) {
=======
    private String name;
    private String email;
    private String token;


    public User() {
    }

    public User(Integer id, String password, String name,String email) {
>>>>>>> main
        this.id = id;
        this.password = password;
<<<<<<< HEAD
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
=======
        this.email = email;
        this.name = name;
    }

>>>>>>> main
}


