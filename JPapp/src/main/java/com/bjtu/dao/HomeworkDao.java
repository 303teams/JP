package com.bjtu.dao;

import com.bjtu.pojo.Homework;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HomeworkDao {
    public List<Homework> findAll();

    public List<Homework> findSimpleAll();

    public Homework findHWById(Integer id);

    public Homework findSimpleHWById(Integer id);

    void insert(Homework homework);

    //    学生查看某项课程的作业列表
    public List<Homework> findHWbyCno(String id,String cno);

    //    老师查看某一课程的学生作业提交列表
    public List<Homework> findHWsbyCno(String cno);

    public void setAnswer (Integer homeworkID,byte[] answer,String Afilename);
    
}
