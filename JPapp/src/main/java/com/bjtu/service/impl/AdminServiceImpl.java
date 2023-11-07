package com.bjtu.service.impl;

import com.bjtu.pojo.Admin;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.Student;
import com.bjtu.pojo.User;
import com.bjtu.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
    @Override
    public RspObject<User> login(Integer id, String password) {
        return null;
    }

    @Override
    public RspObject<Boolean> insert(Admin admin) {
        return null;
    }

    @Override
    public RspObject<List<Admin>> searchAll() {
        return null;
    }

    @Override
    public RspObject<Boolean> deleteOne(Integer id) {
        return null;
    }
}
