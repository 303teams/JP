package com.bjtu.service;

import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.User;

import java.util.List;

public interface UserService {

    public RspObject<User> login(String username, String password);

    public RspObject<Boolean> insert(User user);

    public RspObject<List<User>> searchAll();

    public RspObject<Boolean> deleteOne(String username);

}
