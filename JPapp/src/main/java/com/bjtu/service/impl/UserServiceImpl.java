package com.bjtu.service.impl;

import com.bjtu.config.AuthAccess;
import com.bjtu.dao.UserDao;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.User;
import com.bjtu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {



    @Autowired
    UserDao userDao;
    JavaMailSender javaMailSender;

    private String from="247492078@qq.com";

    private   UserService userService;


    @Override
    public RspObject<User> login(String username, String password) {
        User user = userDao.findByName(username);
        if (user == null) {
            return RspObject.fail("user not exist!");
        } else if (!user.getPassword().equals(password)) {
            return RspObject.fail("wrong password!");
        } else {
            return RspObject.success(user);
        }
    }

    @Override
    public RspObject<Boolean> insert(User user) {
        if(userDao.findByName(user.getUsername()) != null){
            return RspObject.fail("user already exist!",Boolean.FALSE);
        }else{
            userDao.insert(user);
            return RspObject.success(Boolean.TRUE);
        }
    }

    @Override
    public RspObject<List<User>> searchAll() {
        return RspObject.success(userDao.findAll());
    }

    @Override
    public RspObject<Boolean> deleteOne(String username) {
        if(userDao.findByName(username) == null){
            return RspObject.fail("user not exist!",Boolean.FALSE);
        }else{
            userDao.deleteByName(username);
            return RspObject.success(Boolean.TRUE);
        }
    }

    @Override
    public RspObject<Boolean> modifyPassword(String username, String password) {
        userDao.modifyPassword(username,password);
        return RspObject.success(Boolean.TRUE);
    }


    @Override
    public void sendEmailCode(String email){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);

        //邮件收信人
        message.setTo(email);

        //邮件主题
        message.setSubject("title");
        //邮件内容
        message.setText("lzqzzzzz");
        //发送邮件
        javaMailSender.send(message);
        System.out.println("发送邮件成功" );
    }
    @Override
    public User sendEmail(User user) {
        String email = user.getEmail();
        SimpleMailMessage message = new SimpleMailMessage();


        //邮件收信人
        message.setTo(email);
        //邮件主题
        message.setSubject("title");
        //邮件内容
        message.setText("lzqzzzzz");
        //发送邮件
        javaMailSender.send(message);
        System.out.println("发送邮件成功" );
        return null;
    }
//    @AuthAccess
//    @GetMapping("/email/{email}")
//    public int sendemailCode(@PathVariable String email) {
//
//        userService.sendEmailCode(email);
//        return (1);
//    }
}
