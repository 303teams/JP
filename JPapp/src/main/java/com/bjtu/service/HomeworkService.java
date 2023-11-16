package com.bjtu.service;

import com.bjtu.dao.StudentDao;
import com.bjtu.pojo.Homework;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.Student;
import com.bjtu.pojo.User;

import java.util.List;

public interface HomeworkService {

    RspObject<List<Homework>> findAll();
    Homework findHomeworkByThId(String id);
    void addHomework(Homework homework);

    RspObject<List<Homework>> findById(String id,String cno);
//    public RspObject<User> login(String id, String password);
//    public RspObject<Boolean> insert(Student student);
//
//    // public RspObject<List<Student>> searchAll();
//
//    public RspObject<Boolean> deleteOne(String id);
//
//    public RspObject<String> modifyEmail(String email);
//
//    public RspObject<String> modifyPassword(String newPassword, String oldPassword);
//
//    public RspObject<String> changePassword(String id,String password);
//
//    public RspObject<String> modifyInfo(Student student);

    RspObject<Boolean> submitHomework(String id,String homeworkId,String cno,String url);

}
