package com.bjtu.service.impl;

import com.bjtu.dao.UserDao;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.User;
import com.bjtu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

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
}
