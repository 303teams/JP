package com.bjtu.service;

import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.Student;
import com.bjtu.pojo.Teacher;
import com.bjtu.pojo.User;

import java.util.List;

public interface TeacherService {

    public RspObject<User> login(Integer id, String password);

    public RspObject<Boolean> insert(Teacher teacher);

    public RspObject<List<Teacher>> searchAll();

    public RspObject<Boolean> deleteOne(Integer id);

    public RspObject<String> modifyEmail(Integer id, String email);

    public RspObject<String> modifyPassword(Integer id, String password);

}
