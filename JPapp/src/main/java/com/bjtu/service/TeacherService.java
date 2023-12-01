package com.bjtu.service;

import com.bjtu.pojo.*;

import java.util.List;
import java.util.Map;

public interface TeacherService {

    public RspObject<User> login(String id, String password);

    public RspObject<Boolean> insert(Teacher teacher);

    public RspObject<List<Teacher>> searchAll();

    public RspObject<Boolean> deleteOne(String id);

    public RspObject<String> modifyEmail(String email);

    public RspObject<String> modifyPassword(String newPassword,String oldPassword);

    public RspObject<String> changePassword(String id,String password);

    public RspObject<String> modifyInfo(Teacher teacher);

    RspObject<List<Map<String, Object>>> findCourse(String id);

    RspObject<List<Homework>> findHWbyCno(String cno);

    RspObject<List<Content>> findCTByHId(Integer homeworkId);

    RspObject<Boolean> setCTScore(Integer contentId,Integer score);

    RspObject<List<Map<String, Object>>> findAppeal(String id);
    
    Content findCTByID(Integer contentID);

    Appeal findAPByID(Integer contentID);

    RspObject<Boolean> setAP(Integer contentID,int num);
}
