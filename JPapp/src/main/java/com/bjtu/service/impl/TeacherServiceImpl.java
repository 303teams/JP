package com.bjtu.service.impl;

import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.Teacher;
import com.bjtu.pojo.User;
import com.bjtu.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {
    @Override
    public RspObject<User> login(Integer id, String password) {
        return null;
    }

    @Override
    public RspObject<Boolean> insert(Teacher teacher) {
        return null;
    }

    @Override
    public RspObject<List<Teacher>> searchAll() {
        return null;
    }

    @Override
    public RspObject<Boolean> deleteOne(Integer id) {
        return null;
    }
}
