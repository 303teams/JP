package com.bjtu.dao;

import com.bjtu.pojo.Manager;
import com.bjtu.pojo.Teacher;
import com.bjtu.pojo.User;
import com.bjtu.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    public void insert(User user);
    public void deleteByName(String username);
    public List<User> findAll();
    public Student findByStudentName(String username);
    public Teacher findByTeacherName(String username);
    public Manager findByManagerName(String username);
}
