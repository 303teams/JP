package com.bjtu.dao;

import com.bjtu.pojo.Admin;
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
    public Student findByStudentNum(String sno);
    public Teacher findByTeacherNum(String tno);
    public Admin findByAdminNum(String mno);
}
