package com.bjtu.dao;

import com.bjtu.pojo.Teacher;
import com.bjtu.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherDao{

    //    插入
    public void insert(User user);

    //    查询
    public List<User> findAll();
    public Teacher findByNum(Integer num);
    public void deleteByNum(Integer num);

}
