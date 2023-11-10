package com.bjtu.service;

import com.bjtu.dao.StudentDao;
import com.bjtu.pojo.Homework;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.Student;
import com.bjtu.pojo.User;

import java.util.List;

public interface HomeworkService {

    RspObject<List<Homework>> findAll();

    RspObject<List<Homework>> findById(String id);

    RspObject<Boolean> submitHomework(String id,String homeworkId,String cno,String url);

}
