package com.bjtu.service.impl;

import com.bjtu.dao.*;
import com.bjtu.exception.ServiceException;
import com.bjtu.pojo.*;
import com.bjtu.service.AdminService;
import com.bjtu.service.TeacherService;
import com.bjtu.util.TokenUtils;
import com.bjtu.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherDao teacherDao;

    @Autowired
    CourseDao courseDao;

    @Autowired
    HomeworkDao homeworkDao;

    @Autowired
    ContentDao contentDao;

    @Override
    public RspObject<User> login(String id, String password) {
        Teacher teacher = teacherDao.findByNum(id);
        if (teacher == null) {
            return RspObject.fail("该教师不存在!");
        } else if (!teacher.getPassword().equals(password)) {
            return RspObject.fail("密码错误!");
        } else {
            String token = TokenUtils.createToken(id.toString(),password);
            teacher.setToken(token);
            return RspObject.success("登录成功！",teacher);
        }
    }

    @Override
    public RspObject<Boolean> insert(Teacher teacher) {
        if(Utils.userIsExist(teacher.getId())){
            return RspObject.fail("用户已存在!",Boolean.FALSE);
        }else{
            teacherDao.insert(teacher);
            return RspObject.success("插入成功",Boolean.TRUE);
        }
    }

    @Override
    public RspObject<List<Teacher>> searchAll() {
        return RspObject.success("查询成功！",teacherDao.findAll());
    }

    @Override
    public RspObject<Boolean> deleteOne(String id) {
        if(teacherDao.findByNum(id) == null){
            return RspObject.fail("用户不存在!",Boolean.FALSE);
        }else{
            teacherDao.deleteByNum(id);
            return RspObject.success("删除成功！",Boolean.TRUE);
        }
    }

    @Override
    public RspObject<String> modifyEmail(String email) {
        Teacher teacher = (Teacher) TokenUtils.getCurrentUser();
        if(teacher == null){
            throw new ServiceException(500,"用户不存在！");
        }else{
            teacherDao.updateEmail(teacher.getId(),email);
            return RspObject.success("邮箱修改成功！新邮箱为："+ email);
        }
    }

    @Override
    public RspObject<String> modifyPassword(String newPassword,String oldPassword) {
        Teacher teacher = (Teacher) TokenUtils.getCurrentUser();
        if(teacher == null){
            throw new ServiceException(500,"用户不存在！");
        }else if(!teacher.getPassword().equals(oldPassword)){
            throw new ServiceException(500,"原密码错误！");
        }else{
//            teacherDao.updatePassword(teacher.getId(),newPassword);
//            teacherDao.deleteByNum(teacher.getId());
//            teacher.setPassword(newPassword);
//            teacherDao.insert(teacher);
            teacher.setPassword(newPassword);
            teacherDao.updatePassword(teacher);
            return RspObject.success("密码修改成功！");
        }
    }

    @Override
    public RspObject<String> changePassword(String id, String password) {
        Teacher teacher = teacherDao.findByNum(id);
        if(teacher == null){
            throw new ServiceException(500,"用户不存在！");
        }else{
            teacher.setPassword(password);
            teacherDao.updatePassword(teacher);
            return RspObject.success("密码修改成功！");

        }
    }

    @Override
    public RspObject<String> modifyInfo(Teacher teacher) {
        Teacher teacher1 = teacherDao.findByNum(teacher.getId());
        if(teacher == null){
            throw new ServiceException(500,"用户不存在！");
        }else{
            teacherDao.updateInfo(teacher1);
            return RspObject.success("密码修改成功！");
        }
    }
    @Override
    public RspObject<List<Map<String, Object>>> findCourse(String id){
        try {
            List<Map<String, Object>> courses = courseDao.findTHCourse(id);

            if (courses.isEmpty()) {
                return RspObject.success("无课程信息！");
            }

            return RspObject.success("查询成功！",courses);
        } catch (Exception e) {
            e.printStackTrace(); // 记录异常
            return RspObject.fail("查询失败！");
        }
    }

    @Override
    public RspObject<List<Homework>> findHWbyCno(String cno) {
        try {
            List<Homework> homeworks = homeworkDao.findHWsbyCno(cno);

            if (homeworks.isEmpty()) {
                return RspObject.success("无作业信息！");
            }

            return RspObject.success("查询成功！",homeworks);
        } catch (Exception e) {
            e.printStackTrace(); // 记录异常
            return RspObject.fail("查询失败！");
        }
    }

    @Override
    public RspObject<List<Content>> findCTByHId(Integer homeworkID) {
        try{
            List<Content> contents = contentDao.findCTByHId(homeworkID);

            if (contents.isEmpty()){
                return RspObject.success("无作业信息！");
            }
            return RspObject.success("查询成功！",contents);
        }catch (Exception e){
            throw new ServiceException(500,"查询失败！");
        }
    }

    @Override
    public RspObject<Boolean> setCTScore(Integer contentID, Integer score) {
        try {
            contentDao.setCTScore(contentID,score);
            return RspObject.success("修改成功！",Boolean.TRUE);
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }
}
