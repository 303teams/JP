package com.bjtu.dao;

import com.bjtu.pojo.Homework;
import com.bjtu.pojo.Teacher;
import com.bjtu.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TeacherDao{

    //    插入
    public void insert(User user);

    //    查询
    public List<Teacher> findAll();

    public Teacher findByNum(String id);

    public void deleteByNum(String num);

    public void updateInfo(Teacher teacher);

    public void updatePassword(Teacher teacher);

    public void updateEmail(String id, String email);

//    查询教师课程
    public List<Map<String, Object>> findCourse(String id);

//    老师查看某一课程的学生作业提交列表
    public List<Homework> findHWbyCno(String cno);

}
