package com.bjtu.controller;

import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.User;
import com.bjtu.service.EmailService;
import com.bjtu.service.UserService;
import com.bjtu.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public RspObject<User> login( String username,  String password, String role){
        System.out.println(username+" "+password+" "+role);

        Assert.hasLength(username,"用户名不能为空！");
        Assert.hasLength(password,"密码不能为空！");

        if(role.equals("1")){
            return userService.ManagerLogin(username,password);
        }else if(role.equals("2")){
            return userService.StudentLogin(username,password);
        }else if(role.equals("3")) {
            return userService.TeacherLogin(username, password);
        }else{
            return RspObject.fail("角色不存在！");
        }
    }

    @PostMapping("email")
    public RspObject<Object> register(HttpServletRequest request,@RequestParam String email) {
        try {
            // 生成验证码
            String code = Utils.generateVerificationCode();

            session.setAttribute("vcode",code);
            System.out.println(session.getAttribute("vcode"));
            System.out.println(1);
            emailService.sendSimpleMessage(email, "注册验证码", "您的验证码是：" + code);
            System.out.println(2);
            return RspObject.success("验证码已发送至您的邮箱");
        } catch (Exception e) {
            return RspObject.fail("验证码未发送至您的邮箱");
        }
    }

    @PostMapping("/verify")
    public RspObject<Object> verify(HttpServletRequest request, String code){
        System.out.println(session.getAttribute("vcode"));
        if(session.getAttribute("vcode").toString().equals(code)){
            return RspObject.success("验证码正确！");
        }else{
            return RspObject.fail("验证码错误！");
        }
    }

}
