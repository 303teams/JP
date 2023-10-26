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
