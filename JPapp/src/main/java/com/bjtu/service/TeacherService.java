package com.bjtu.service;

import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.Student;
import com.bjtu.pojo.Teacher;
import com.bjtu.pojo.User;

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
}
