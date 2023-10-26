package com.bjtu.dao;

import com.bjtu.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {


    public void insert(User user);
    public void deleteByName(String username);
    public List<User> findAll();
    public User findByName(String username);
    String getEmail();
}
