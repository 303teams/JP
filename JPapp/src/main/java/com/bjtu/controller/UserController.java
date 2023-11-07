package com.bjtu.controller;

import com.bjtu.config.AuthAccess;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.User;
import com.bjtu.service.AdminService;
import com.bjtu.service.StudentService;
import com.bjtu.service.TeacherService;
import com.bjtu.service.impl.EmailService;

import com.bjtu.util.TokenUtils;
import com.bjtu.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.bjtu.exception.ServiceException;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    AdminService adminService;

    @Autowired
    EmailService emailService;

    @Autowired
    private HttpSession session;

    @AuthAccess
    @PostMapping("login")
    public RspObject<User> login(String username, String password, String role){
        System.out.println(username+" "+password+" "+role);

//        异常捕获
        Assert.hasLength(username,"用户名不能为空！");
        Assert.hasLength(password,"密码不能为空！");

        Integer id = Integer.parseInt(username);
        if(role.equals("1")){
            return adminService.login(id,password);
        }else if(role.equals("2")){
            return studentService.login(id,password);
        }else if(role.equals("3")) {
            return teacherService.login(id, password);
        }else{
            return RspObject.fail("登录失败！");
        }
    }

    @AuthAccess
    @PostMapping("email")
    public RspObject<Object> email(String username,String email) {

        Assert.hasLength(username,"用户名不能为空！");
        Assert.hasLength(email,"邮箱不能为空！");

        Integer id = Integer.parseInt(username);
        System.out.println(id+" "+email);
        if(!Utils.isMatchEmail(id,email)){
            System.out.println("用户账号与邮箱不匹配！");
            throw new ServiceException(500,"用户账号与邮箱不匹配！");
        }

        try{
            // 生成验证码
            String code = Utils.generateVerificationCode();
            session.setAttribute("vcode",code);
            emailService.sendSimpleMessage(email, "注册验证码", "您的验证码是：" + code);
            return RspObject.success("验证码已发送至您的邮箱");
        } catch (Exception e) {
            throw new ServiceException("验证码未发送至您的邮箱");
        }

    }

    @AuthAccess
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
