package com.bjtu.dao;

import com.bjtu.pojo.Homework;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HomeworkDao {
    public List<Homework> findAll();

    public Homework findHWById(Integer id);

    void insert(Homework homework);

    //    学生查看某项课程的作业列表
    public List<Homework> findHWbyCno(String id,String cno);

    //    老师查看某一课程的学生作业提交列表
    public List<Homework> findHWsbyCno(String cno);

    public void setAnswer (Integer homeworkID,byte[] answer,String Afilename);

}
