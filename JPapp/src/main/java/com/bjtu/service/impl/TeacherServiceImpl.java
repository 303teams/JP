package com.bjtu.service.impl;

import com.bjtu.dao.*;
import com.bjtu.exception.ServiceException;
import com.bjtu.pojo.*;
import com.bjtu.service.TeacherService;
import com.bjtu.util.TokenUtils;
import com.bjtu.util.AcountUtils;
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
    AppealDao appealDao;

    @Autowired
    ContentDao contentDao;

    @Autowired
    ScoreDao scoreDao;

    @Override
    public RspObject<User> login(String id, String password) {
        Teacher teacher = teacherDao.findByNum(id);
        if (teacher == null) {
            return RspObject.fail("该教师不存在!");
        } else if (!AcountUtils.userIsOK(teacher.getId())){
            return RspObject.fail("该账号未激活！");
        }else if(!teacher.getPassword().equals(password)) {
            return RspObject.fail("密码错误!");
        }else if(teacher.getExist() != 1){
            return RspObject.fail("该教师已不在本校任教!");
        } else {
            String token = TokenUtils.createToken(id.toString(),password);
            teacher.setToken(token);
            return RspObject.success("登录成功！",teacher);
        }
    }

    @Override
    public RspObject<Boolean> insert(Teacher teacher) {
        if(AcountUtils.userIsExist(teacher.getId())){
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
        Teacher teacher1 = (Teacher) TokenUtils.getCurrentUser();
        if(teacher1 == null){
            throw new ServiceException(500,"用户不存在！");
        }else{
            teacher.setId(teacher1.getId());
            teacherDao.updateInfo(teacher);
            return RspObject.success("信息修改成功！");
        }
    }
    @Override
    public RspObject<List<Map<String, Object>>> findCourse(String id){
        try {
            List<Map<String, Object>> courses = courseDao.findTHCourse(id);
            if (courses.size() == 1 && courses.get(0) == null) {
                return RspObject.success("无课程信息！");
            }
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
            throw new ServiceException(500,e.getMessage());
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
            throw new ServiceException(500,e.getMessage());
        }
    }

    @Override
    public RspObject<Boolean> setCTScore(Integer contentID, double score) {
        try {
            contentDao.setCTScore(contentID,score);
            return RspObject.success("修改成功！",Boolean.TRUE);
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }

    @Override
    public RspObject<List<Map<String, Object>>> findAppeal(String id){
        try {
            List<Map<String, Object>> appeals = appealDao.findAppeal(id);

            if (appeals.isEmpty()) {
                return RspObject.success("无申诉信息！");
            }

            System.out.println(appeals);

            return RspObject.success("查询成功！",appeals);
        } catch (Exception e) {
            e.printStackTrace(); // 记录异常
            return RspObject.fail("查询失败！");
        }
    }

    @Override
    public Content findCTByID(Integer contentID){

        return contentDao.findCTById(contentID);
    }

    @Override
    public  Appeal findAPByID(Integer appealID){

        return appealDao.findAPByID(appealID);
    }

    @Override
    public RspObject<Boolean> setAP(Integer appealID,Integer num){
        appealDao.setAP(appealID,num);
        return RspObject.success("修改成功！",Boolean.TRUE);
    }

    @Override
    public RspObject<Map<String, Object>> findAPByAID(Integer appealID) {
        try{
            return RspObject.success("查询成功！",appealDao.findAPByAID(appealID));
        }catch (Exception e){
            throw new ServiceException(500,"查询失败!");
        }
    }

    @Override
    public RspObject<Boolean> deleteAPByAID(Integer appealID) {
        try{
            appealDao.deleteAPByAID(appealID);
            return RspObject.success("删除成功！",Boolean.TRUE);
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }

    @Override
    public RspObject<Boolean> setCTWeightedScore(Integer contentId, Integer score) {
//        学生打分权重40%，老师打分权重60%
        List<Score> scores = scoreDao.findSCByCID(contentId);
        Integer sum = 0;
        for (Score sc : scores) {
            sum += sc.getScore();
        }
        try{
            contentDao.setCTScore(contentId, (int) (0.6*score+0.4*sum/scores.size()));
            return RspObject.success("修改成功！",Boolean.TRUE);
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }

    /**
     * 查找相似作业列表
     * @param contentID
     * @return
     */
    @Override
    public RspObject<List<Map<String,Object>>> findSimilarCTs(Integer contentID) {
        try{
            return RspObject.success("查询成功！",contentDao.findSimilarCTs(contentID));
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }

}
