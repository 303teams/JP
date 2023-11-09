package com.bjtu.dao;

import com.bjtu.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentDao {
    //    插入
    public void insert(Student student);
    //    查询
    public List<Student> findAll();
    public Student findByNum(String id);
    public void deleteByNum(String id);
    public void updateInfo(Student student);

    public void updatePassword(String id, String password);

    public void updateEmail(String id, String email);
}
