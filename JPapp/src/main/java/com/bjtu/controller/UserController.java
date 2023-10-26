package com.bjtu.controller;

import com.bjtu.config.AuthAccess;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.User;
import com.bjtu.service.EmailService;
import com.bjtu.service.UserService;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bjtu.util.Utils;


import java.rmi.ServerException;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;


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

    @PostMapping("email")
    public RspObject<Object> register(@RequestParam String email) {
        try {
            // 生成验证码
            String code = Utils.generateVerificationCode();
            // 将验证码存储到Redis中
//            redisTemplate.opsForValue().set(redisKey, code, 5, TimeUnit.MINUTES);
            // 发送注册邮件
//            System.out.println(email);
//            System.out.println(code);
            System.out.println(1);
            emailService.sendSimpleMessage(email, "注册验证码", "您的验证码是：" + code);
            System.out.println(2);
            return RspObject.success("验证码已发送至您的邮箱");
        } catch (Exception e) {
            return RspObject.fail("验证码未发送至您的邮箱");
        }
    }

}
