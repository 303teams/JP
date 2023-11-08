package com.bjtu.service.impl;

import com.bjtu.dao.StudentDao;
import com.bjtu.exception.ServiceException;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.Student;
import com.bjtu.pojo.User;
import com.bjtu.service.StudentService;
import com.bjtu.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("studentService")
public class StudentServiceImpl implements StudentService  {

    @Autowired
    StudentDao studentDao;

    @Override
    public RspObject<User> login(Integer id, String password) {
//        System.out.println(id + " " + password);
        Student student = studentDao.findByNum(id);
//        System.out.println(student);
        if (student == null) {
            return RspObject.fail("该学生不存在!");
        } else if (!student.getPassword().equals(password)) {
            return RspObject.fail("密码错误!");
        } else {
            String token = TokenUtils.createToken(id.toString(),password);
            student.setToken(token);
            return RspObject.success(student);
        }
    }

    @Override
    public RspObject<Boolean> insert(Student student) {
        if(studentDao.findByNum(student.getId()) != null){
            return RspObject.fail("user already exist!",Boolean.FALSE);
        }else{
            studentDao.insert(student);
            return RspObject.success(Boolean.TRUE);
        }
    }

    @Override
    public RspObject<List<User>> searchAll() {
        return null;
    }

    @Override
    public RspObject<Boolean> deleteOne(Integer id) {
        return null;
    }


}
