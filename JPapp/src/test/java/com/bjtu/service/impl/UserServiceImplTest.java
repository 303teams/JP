package com.bjtu.service.impl;

import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

//    @Autowired
//    UserService userService;

//    @Test
//    void login() {
//        System.out.println(userService.login("lzc","111"));
//    }
//
//    @Test
//    void insert() {
//        User user = new User("lzc","111");
//        RspObject rsp = userService.insert(user);
//        Boolean t = (Boolean)rsp.getData();
//        if(t){
//            System.out.println("ok");
//        }else{
//            System.out.println("no");
//        }
//        System.out.println(rsp);
//    }
//
//    @Test
//    void searchAll() {
//        userService.searchAll().getData().forEach(System.out::println);
//    }
//
//    @Test
//    void deleteOne() {
//        System.out.println(userService.deleteOne("lzc"));
//    }
}