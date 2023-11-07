package com.bjtu.service;

import com.bjtu.pojo.Admin;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.Student;
import com.bjtu.pojo.User;

import java.util.List;

public interface AdminService {

    public RspObject<User> login(Integer id, String password);

    public RspObject<Boolean> insert(Admin admin);

    public RspObject<List<Admin>> searchAll();

    public RspObject<Boolean> deleteOne(Integer id);

}
