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
    public RspObject<User> login(String id, String password) {
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
    public RspObject<Boolean> deleteOne(String id) {
        return null;
    }

    @Override
    public RspObject<String> modifyEmail(String email) {
        Student student = (Student) TokenUtils.getCurrentUser();
        if(student == null){
            throw new ServiceException(500,"用户状态异常！");
        }else{
            studentDao.updateEmail(student.getId(),email);
            return RspObject.success("邮箱修改成功！新邮箱为："+ email);
        }
    }

    @Override
    public RspObject<String> modifyPassword(String newPassword, String oldPassword) {
//        根据请求上下文获取用户对象信息
        Student student = (Student) TokenUtils.getCurrentUser();
        if(student == null){
            throw new ServiceException(500,"用户不存在！");
        }else if(!student.getPassword().equals(oldPassword)) {
//            旧密码与新密码不一致
            throw new ServiceException(500,"原密码错误！");
        }else{
            studentDao.updatePassword(student.getId(),newPassword);
            return RspObject.success("密码修改成功!");
        }
    }

    @Override
    public RspObject<String> changePassword(String id, String password) {
        Student student = studentDao.findByNum(id);
        if(student == null){
            throw new ServiceException(500,"用户不存在！");
        }else{
            studentDao.updatePassword(id,password);
            return RspObject.success("密码修改成功!");
        }
    }

    @Override
    public RspObject<String> modifyInfo(Student student) {
        Student student1 = studentDao.findByNum(student.getId());
        if(student1 == null){
            throw new ServiceException(500,"用户不存在！");
        }else{
            studentDao.updateInfo(student);
            return RspObject.success("信息修改成功!");
        }
    }


}
