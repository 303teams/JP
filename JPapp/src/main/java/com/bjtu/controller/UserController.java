package com.bjtu.controller;

import com.bjtu.config.AuthAccess;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.User;
import com.bjtu.service.UserService;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @AuthAccess
    @GetMapping("/email/{email}")
    public int sendEmailCode(@PathVariable String email){

        userService.sendEmailCode(email);
        return 1;
    }

    @PostMapping("login")
    public RspObject<User> login( String username,  String password, String role){

        System.out.println(username+" "+password);

        Assert.hasLength(username,"用户名不能为空！");
        Assert.hasLength(password,"密码不能为空！");

        return userService.login(username,password);
    }

    @PostMapping("register")
    public RspObject<Boolean> register(String username,String password){
        Assert.hasLength(username,"用户名不能为空！");
        Assert.hasLength(password,"密码不能为空！");

        User user = new User(username,password);

        return userService.insert(user);
    }

    @PostMapping("searchAll")
    public RspObject<List<User>> searchAll(){
        return userService.searchAll();
    }

    @PostMapping("delete")
    public RspObject<Boolean> delete(String username){
        Assert.hasLength(username,"用户名不能为空！");
        return userService.deleteOne(username);
    }

}
