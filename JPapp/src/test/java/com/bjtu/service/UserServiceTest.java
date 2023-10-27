package com.bjtu.service;

import com.bjtu.dao.UserDao;
import com.bjtu.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void modifyPassword() {
        UserServiceImpl userService = new UserServiceImpl();
//        userService.modifyPassword("111","222");
        userService.deleteOne("333");
    }

}