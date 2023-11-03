package com.bjtu.service.impl;

import com.bjtu.dao.UserDao;
import com.bjtu.pojo.*;
import com.bjtu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public RspObject<User> StudentLogin(String username, String password) {
        Student student = userDao.findByStudentNum(username);
        if (student == null) {
            return RspObject.fail("该学生不存在!");
        } else if (!student.getSpassword().equals(password)) {
            return RspObject.fail("密码错误!");
        } else {
            return RspObject.success(student);
        }
    }

    @Override
    public RspObject<User> TeacherLogin(String username, String password) {
        Teacher teacher = userDao.findByTeacherNum(username);
        if (teacher == null) {
            return RspObject.fail("该老师不存在!");
        } else if (!teacher.getTpassword().equals(password)) {
            return RspObject.fail("密码错误!");
        } else {
            return RspObject.success(teacher);
        }
    }

    @Override
    public RspObject<User> AdminLogin(String username, String password) {
        Admin admin = userDao.findByAdminNum(username);
        if (admin == null) {
            return RspObject.fail("该管理员不存在!");
        } else if (!admin.getMpassword().equals(password)) {
            return RspObject.fail("密码错误!");
        } else {
            return RspObject.success(admin);
        }
    }



    @Override
    public RspObject<Boolean> insert(User user) {
        if(userDao.findByStudentNum(user.getUsername()) != null){
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
        if(userDao.findByStudentNum(username) == null){
            return RspObject.fail("user not exist!",Boolean.FALSE);
        }else{
            userDao.deleteByName(username);
            return RspObject.success(Boolean.TRUE);
        }
    }
}
