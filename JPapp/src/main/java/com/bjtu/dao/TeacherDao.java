package com.bjtu.dao;

import com.bjtu.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherDao{

    //    插入
    public void insert(User user);

    //    查询
    public List<User> findAll();
    public User findByNum(Integer num);
    public void deleteByNum(Integer num);

}
