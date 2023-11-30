package com.bjtu.service;

import com.bjtu.dao.StudentDao;
import com.bjtu.pojo.*;

import java.util.List;
import java.util.Map;

public interface StudentService {

    public RspObject<User> login(String id, String password);
    public RspObject<Boolean> insert(Student student);

   // public RspObject<List<Student>> searchAll();

    public RspObject<Boolean> deleteOne(String id);

    public RspObject<String> modifyEmail(String email);

    public RspObject<String> modifyPassword(String newPassword, String oldPassword);

    public RspObject<String> changePassword(String id,String password);

    public RspObject<String> modifyInfo(Student student);

//    RspObject<List<String>> findAll();
    public RspObject<List<Map<String, Object>>> findCourse(String id);

//    RspObject<List<Student>> findAll();
    RspObject<List<Homework>> findHWbyCno(String id,String cno);

    RspObject<List<Content>> findCTsByCID(Integer contentID);

    RspObject<Boolean> setScore(Integer contentID, Integer score, String sno);
    public RspObject<Boolean> insertScore(Score score);

    public RspObject<Boolean> insertAppeal(Appeal appeal);

}
