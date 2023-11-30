package com.bjtu.service.impl;

import com.bjtu.dao.*;
import com.bjtu.exception.ServiceException;
import com.bjtu.pojo.*;
import com.bjtu.service.StudentService;
import com.bjtu.util.TokenUtils;
import com.bjtu.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("studentService")
public class StudentServiceImpl implements StudentService  {

    @Autowired
    StudentDao studentDao;
    @Autowired
    AppealDao appealDao;
    @Autowired
    ScoreDao scoreDao;
    @Autowired
    CourseDao courseDao;

    @Autowired
    HomeworkDao homeworkDao;

    @Autowired
    ContentDao contentDao;

    @Override
    public RspObject<User> login(String id, String password) {
//        System.out.println(id + " " + password);
        Student student = studentDao.findByNum(id);
//        System.out.println(student);
        if (student == null) {
            return RspObject.fail("该学生不存在!");
        } else if (!student.getPassword().equals(password)) {
            System.out.println(student.getPassword());
            System.out.println(password);
            return RspObject.fail("密码错误!");
        } else {
            System.out.println(student.getPassword());
            String token = TokenUtils.createToken(id.toString(),password);
            student.setToken(token);
            return RspObject.success("登录成功！",student);
        }
    }

    @Override
    public RspObject<Boolean> insert(Student student) {
        if(Utils.userIsExist(student.getId())){
            return RspObject.fail("用户已存在!",Boolean.FALSE);
        }else{
            studentDao.insert(student);
            return RspObject.success("插入成功",Boolean.TRUE);
        }
    }

//    @Override
//    public RspObject<List<Student>> searchAll() {
//        return RspObject.success("查询成功！",studentDao.findAll());
//    }

    @Override
    public RspObject<Boolean> deleteOne(String id) {
        if(studentDao.findByNum(id) == null){
            return RspObject.fail("用户不存在!",Boolean.FALSE);
        }else{
            studentDao.deleteByNum(id);
            return RspObject.success("删除成功！",Boolean.TRUE);
        }
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
            student.setPassword(newPassword);
            studentDao.updatePassword(student);
//            studentDao.updatePassword(student.getId(),newPassword);
//            studentDao.deleteByNum(student.getId());
//            student.setPassword(newPassword);
//            studentDao.insert(student);
            return RspObject.success("密码修改成功!");
        }
    }

    @Override
    public RspObject<String> changePassword(String id, String password) {
        Student student = studentDao.findByNum(id);
        if(student == null){
            throw new ServiceException(500,"用户不存在！");
        }else{
            student.setPassword(password);
            studentDao.updatePassword(student);
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

//    @Override
//    public RspObject<List<Student>> findAll(){
//        return RspObject.success("查询成功！",studentDao.findAll());
//    }


    public RspObject<List<Map<String, Object>>> findCourse(String id){
        try {
            List<Map<String, Object>> courses = courseDao.findSTCourse(id);

            if (courses.isEmpty()) {
                return RspObject.success("无课程信息！");
            }
            return RspObject.success("查询成功！",courses);
        } catch (Exception e) {
            throw new ServiceException(500,e.getMessage());
        }
    }

    @Override
    public RspObject<List<Homework>> findHWbyCno(String id, String cno) {
        try {
            List<Homework> contents = homeworkDao.findHWbyCno(id,cno);

            if (contents.isEmpty()) {
                return RspObject.success("无作业信息！");
            }

            return RspObject.success("查询成功！",contents);
        } catch (Exception e) {
            e.printStackTrace(); // 记录异常
            return RspObject.fail("查询失败！");
        }
    }

    @Override
    public RspObject<List<Content>> findCTsByCID(Integer contentID) {
        String id = contentDao.findSnoByCID(contentID);
        try {
            Content content = contentDao.findCTSByCID(contentID);
            Integer[] temp = content.getContents();
            List<Content> contents = new ArrayList<>();
            for(int i = 0; i<6; i++){
                contents.add(contentDao.findxCTById(temp[i],id));
            }
            return RspObject.success("查询成功！", contents);
        }catch (Exception e){
            throw new ServiceException(500,"查询失败！!");
        }
    }

    @Override
    public RspObject<Boolean> setScore(Integer contentID, Integer score, String sno) {
        try {
            scoreDao.setCTScore(contentID,score,sno);
            return RspObject.success("修改成功！",Boolean.TRUE);
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }

    @Override
    public RspObject<Boolean> insertScore(Score score) {
        try {
            scoreDao.insertScore(score);
            return RspObject.success("插入成功！",Boolean.TRUE);
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }

    @Override
    public RspObject<Boolean> insertAppeal(Appeal appeal) {
        try {
            appealDao.insertAppeal(appeal);
            return RspObject.success("插入成功！",Boolean.TRUE);
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }
}
