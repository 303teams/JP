package com.bjtu.controller;

import com.bjtu.config.AuthAccess;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.User;
import com.bjtu.service.EmailService;
import com.bjtu.service.UserService;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;


import org.springframework.web.bind.annotation.*;
import com.bjtu.util.Utils;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.rmi.ServerException;
import java.util.List;


@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Autowired
    private HttpSession session;


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
    public RspObject<Object> register(HttpServletRequest request,@RequestParam String username,@RequestParam String email) {
        try {
            // 生成验证码
            String code = Utils.generateVerificationCode();

            session.setAttribute("code",code);
            session.setAttribute("username",username);
//            System.out.println(email);
//            System.out.println(code);
//            System.out.println(1);
            emailService.sendSimpleMessage(email, "注册验证码", "您的验证码是：" + code);
//            System.out.println(2);
            return RspObject.success("验证码已发送至您的邮箱");
        } catch (Exception e) {
            return RspObject.fail("验证码未发送至您的邮箱");
        }
    }
    @PostMapping("/verify")
    public RspObject<Object> verify(HttpServletRequest request,String code){

        System.out.println(session.getAttribute("code"));
//        System.out.println(request.getSession());

        if(session.getAttribute("code").toString().equals(code)){
            System.out.println("okkkkk");
            return RspObject.success("验证通过！");
        }else{
            return RspObject.fail("验证不通过！");
        }
    }

    @PostMapping("/change")
    public RspObject<Object> change(String password){

//        System.out.println(username+first+" "+second);
        userService.modifyPassword(session.getAttribute("username").toString(),password);
            return RspObject.success("修改成功！");
//        }else{
//            return "两次输入的密码不匹配！";
//        }
    }


    @GetMapping("/setSessoin")
    public RspObject<Object> setSessoin(HttpServletRequest request){
        session.setAttribute("title","我是session里的");
        return RspObject.success("setok");
    }

    @GetMapping("/getSessoin")
    public RspObject<Object> getSessoin(HttpServletRequest request){
        System.out.println(session.getAttribute("title"));
        return RspObject.success("getok");
    }

}
