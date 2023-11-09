package com.bjtu.dao;

import com.bjtu.pojo.Admin;
import com.bjtu.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminDao{

    //    插入
    public void insert(Admin admin);
    //    查询
    public List<User> findAll();
    public Admin findByNum(Integer num);
    public void deleteByNum(Integer num);

    public void updateRecord(Admin admin);
}
