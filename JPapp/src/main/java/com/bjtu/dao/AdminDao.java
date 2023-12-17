package com.bjtu.dao;

import com.bjtu.pojo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminDao{

    //    插入
    public void insert(Admin admin);
    //    查询
    public List<User> findAll();
    public Admin findByNum(String num);
    public void deleteByNum(String num);

//    public void updatePassword(String id,String password);

    public void updateEmail(String id, String email);

    void insertStudent(Student student);

    void insertTeacher(Teacher teacher);

    void insertCourse(Course course);

    void deleteStudentByID(String id);

    void deleteTeacherByID(String id);

    void deleteCourseByCno(String cno);

    public Student findStudentByID(String id);

    public Teacher findTeacherByID(String id);

    public Course findCourseByCno(String cno);
}
