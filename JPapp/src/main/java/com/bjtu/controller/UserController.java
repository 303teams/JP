package com.bjtu.controller;

import com.bjtu.config.AuthAccess;
import com.bjtu.pojo.*;
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
import org.springframework.web.bind.annotation.RestController;

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
//        System.out.println(username+" "+password+" "+role);

//        异常捕获
        Assert.hasLength(username,"用户名不能为空！");
        Assert.hasLength(password,"密码不能为空！");

//        后端存储用户id
        session.setAttribute("id",username);

        if(role.equals("admin")){
            return adminService.login(username,password);
        }else if(role.equals("student")){
            return studentService.login(username,password);
        }else if(role.equals("teacher")) {
            return teacherService.login(username, password);
        }else{
            return RspObject.fail("登录失败！");
        }
    }

    @AuthAccess
    @PostMapping("email")
    public RspObject<Object> email(String username,String email) {

        Assert.hasLength(username,"用户名不能为空！");
        Assert.hasLength(email,"邮箱不能为空！");

        if(!Utils.isMatchEmail(username,email)){
            System.out.println("用户账号与邮箱不匹配！");
            throw new ServiceException(500,"用户账号与邮箱不匹配！");
        }
        try{
            // 生成验证码
            String code = Utils.generateVerificationCode();
            session.setAttribute("id",username);
            session.setAttribute("vcode",code);
            emailService.sendSimpleMessage(email, "验证码", "您的验证码是：" + code);
            return RspObject.success("验证码已发送至您的邮箱");
        } catch (Exception e) {
            throw new ServiceException("验证码未发送至您的邮箱");
        }
    }

    @AuthAccess
    @PostMapping("/verify")
    public RspObject<Object> verify(String code){
//        System.out.println(session);
//        System.out.println(session.getAttribute("vcode"));
        if(session.getAttribute("vcode").toString().equals(code)){
            System.out.println("验证码正确！");
            session.setAttribute("vcode",null);
            return RspObject.success("验证码正确！");
        }else{
            System.out.println("验证码错误！");
            session.setAttribute("vcode",null);
            return RspObject.fail("验证码错误！");
        }
    }


    @AuthAccess
    @PostMapping("/change")
    public RspObject<String> change(String password){
        String id = session.getAttribute("id").toString();
        String role = Utils.getUserType(id);
        if(role.equals("admin")){
            return adminService.changePassword(id,password);
        }else if(role.equals("student")){
            return studentService.changePassword(id,password);
        }else if(role.equals("teacher")) {
            return teacherService.changePassword(id, password);
        }else{
            return RspObject.fail("修改失败！");
        }
    }

    @PostMapping("/modifyPassword")
    public RspObject<String> modifyPassword(String newPassword,String oldPassword){
        User user = TokenUtils.getCurrentUser();
        if(user.getClass() == Student.class){
            return studentService.modifyPassword(newPassword,oldPassword);
        }else if(user.getClass() == Admin.class){
            return teacherService.modifyPassword(newPassword,oldPassword);
        }else if(user.getClass() == Teacher.class){
            return adminService.modifyPassword(newPassword,oldPassword);
        }else{
            return RspObject.fail("修改密码失败！");
        }
//        String id = session.getAttribute("id").toString();
//        System.out.println("当前用户："+id);
//        String role = Utils.getUserType(id);
//        if(role.equals("admin")){
//            return adminService.modifyPassword(newPassword,oldPassword);
//        }else if(role.equals("student")){
//            return studentService.modifyPassword(newPassword,oldPassword);
//        }else if(role.equals("teacher")) {
//            return teacherService.modifyPassword(newPassword, oldPassword);
//        }else{
//            return RspObject.fail("修改密码失败！");
//        }


    }

    @PostMapping("/modifyEmail")
    public RspObject<String> modifyEmail(String code,String email){

        if(!session.getAttribute("vcode").toString().equals(code)){
            session.setAttribute("vcode",null);
            return RspObject.fail("验证码错误！");
        }
        User user = TokenUtils.getCurrentUser();
        if(user.getClass() == Student.class){
            return studentService.modifyEmail(email);
        }else if(user.getClass() == Admin.class){
            return teacherService.modifyEmail(email);
        }else if(user.getClass() == Teacher.class){
            return adminService.modifyEmail(email);
        }else{
            return RspObject.fail("修改邮箱失败！");
        }
    }

    @PostMapping("sendEmail")
    public RspObject<String> sendEmail(String email){
        try{
            // 生成验证码
            String code = Utils.generateVerificationCode();
            session.setAttribute("vcode",code);
            emailService.sendSimpleMessage(email, "绑定邮箱验证码", "您的验证码是：" + code);
            return RspObject.success("验证码已发送至您的邮箱");
        } catch (Exception e) {
            throw new ServiceException("验证码未发送至您的邮箱");
        }
    }





}
