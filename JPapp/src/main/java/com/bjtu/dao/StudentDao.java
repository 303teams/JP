package com.bjtu.dao;

import com.bjtu.pojo.*;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentDao {
    //    插入
    public void insert(Student student);
    //    查询
    public List<Student> findAll();
    public Student findByNum(String id);
    public void deleteByNum(String id);
    void reviveStudent(String id);
    public void updateInfo(Student student);
    public void updatePassword(Student student);
    public void updateEmail(String id, String email);

    public void alterStudentByID(Student student);

}
