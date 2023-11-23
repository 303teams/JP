package com.bjtu.service;

import com.bjtu.dao.StudentDao;
import com.bjtu.pojo.Homework;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.Student;
import com.bjtu.pojo.User;

import java.util.List;

public interface HomeworkService {

    RspObject<List<Homework>> findAll();


//    返回一个课程下的所有学生的作业
    Homework findHWById(Integer id);

    void addHomework(Homework homework);

}