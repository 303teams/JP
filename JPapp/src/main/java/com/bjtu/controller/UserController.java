package com.bjtu.controller;

import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.User;
import com.bjtu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("login")
    public RspObject<User> login( String username,  String password){
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

    public RspObject<Boolean> isMatchEmail(String email){
        Assert.hasLength(email,"邮箱不能为空！");
        return userService.isMatchEmail(email);
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
