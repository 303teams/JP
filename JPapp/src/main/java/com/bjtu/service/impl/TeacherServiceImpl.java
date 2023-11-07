package com.bjtu.service.impl;

import com.bjtu.dao.AdminDao;
import com.bjtu.dao.StudentDao;
import com.bjtu.dao.TeacherDao;
import com.bjtu.exception.ServiceException;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.Student;
import com.bjtu.pojo.Teacher;
import com.bjtu.pojo.User;
import com.bjtu.service.AdminService;
import com.bjtu.service.TeacherService;
import com.bjtu.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherDao teacherDao;

    @Override
    public RspObject<User> login(Integer id, String password) {
        System.out.println(id + " " + password);
        Teacher teacher = teacherDao.findByNum(id);
//        System.out.println(student);
        if (teacher == null) {
            return RspObject.fail("该教师不存在!");
        } else if (!teacher.getPassword().equals(password)) {
            return RspObject.fail("密码错误!");
        } else {
            String token = TokenUtils.createToken(id.toString(),password);
            teacher.setToken(token);
            return RspObject.success(teacher);
        }
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
