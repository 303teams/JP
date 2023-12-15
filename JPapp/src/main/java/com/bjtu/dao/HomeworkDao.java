package com.bjtu.dao;

import com.bjtu.pojo.Homework;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HomeworkDao {
    public List<Homework> findAll();

    public List<Homework> findSimpleAll();

    public Homework findHWById(Integer id);

    public Homework findSimpleHWById(Integer id);

    void insert(Homework homework);

    void deleteByHId(Integer homeworkID);

    //    学生查看某项课程的作业列表
    public List<Homework> findHWbyCno(String id,String cno);

    //    老师查看某一课程的学生作业提交列表
    public List<Homework> findHWsbyCno(String cno);

    public void setAnswer (Integer homeworkID,byte[] answer,String Afilename);

    public void updateScoreDdl(Integer homeworkID,String scoreDdl);

    public void updateSubmitDdl(Integer homeworkID,String submitDdl);
}
