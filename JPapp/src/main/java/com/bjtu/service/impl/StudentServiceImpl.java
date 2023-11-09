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

import javax.annotation.Resource;
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
            return RspObject.success("登录成功！",student);
        }
    }

    @Override
    public RspObject<Boolean> insert(Student student) {
        if(studentDao.findByNum(student.getId()) != null){
            return RspObject.fail("user already exist!",Boolean.FALSE);
        }else{
            studentDao.insert(student);
            return RspObject.success("插入成功",Boolean.TRUE);
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

    @Override
    public RspObject<String> modifyEmail(Integer id, String email) {
        Student student = studentDao.findByNum(id);
        if(student == null){
            throw new ServiceException(500,"用户不存在！");
        }else{
            student.setEmail(email);
            studentDao.updateRecord(student);
            return RspObject.success("邮箱修改成功！新邮箱为："+ email);
        }
    }

    @Override
    public RspObject<String> modifyPassword(Integer id, String password) {
        Student student = studentDao.findByNum(id);
        if(student == null){
            throw new ServiceException(500,"用户不存在！");
        }else{
            student.setPassword(password);
            studentDao.updateRecord(student);
            return RspObject.success("密码修改成功!");
        }
    }


}
