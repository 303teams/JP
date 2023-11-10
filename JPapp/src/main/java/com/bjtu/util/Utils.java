package com.bjtu.util;

import com.bjtu.dao.AdminDao;
import com.bjtu.dao.StudentDao;
import com.bjtu.dao.TeacherDao;
import com.bjtu.exception.ServiceException;
import com.bjtu.pojo.Admin;
import com.bjtu.pojo.Student;
import com.bjtu.pojo.Teacher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Random;

@Component
public class Utils {

    private static StudentDao staticStudentDao;
    private static TeacherDao staticTeachertDao;
    private static AdminDao staticAdminDao;

    @Resource
    StudentDao studentDao;
    @Resource
    TeacherDao teacherDao;
    @Resource
    AdminDao adminDao;

    @PostConstruct
    public void setUserService() {
        staticStudentDao = studentDao;
        staticTeachertDao = teacherDao;
        staticAdminDao = adminDao;
    }

    public static String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(8999) + 1000;
        return String.valueOf(code);
    }

//    验证邮箱是否与账户名匹配
    public static boolean isMatchEmail(String id,String email){
        Student student = staticStudentDao.findByNum(id);
        Teacher teacher = staticTeachertDao.findByNum(id);
        Admin admin = staticAdminDao.findByNum(id);
        if(student != null){
            return student.getEmail().equals(email);
        }else if(teacher != null){
            return teacher.getEmail().equals(email);
        }else if(admin != null){
            return admin.getEmail().equals(email);
        }else{
            throw new ServiceException(500,"该用户不存在!");
        }
    }

    public static String getUserType(String id){
        Student student = staticStudentDao.findByNum(id);
        Teacher teacher = staticTeachertDao.findByNum(id);
        Admin admin = staticAdminDao.findByNum(id);
        if(student != null){
            return "student";
        }else if(teacher != null){
            return "teacher";
        }else if(admin != null){
            return "admin";
        }else{
            throw new ServiceException(500,"该用户不存在!");
        }
    }

    public static boolean userIsExist(String id){
        if(staticStudentDao.findByNum(id) != null){
            return true;
        }else if(staticTeachertDao.findByNum(id) != null){
            return true;
        }else if(staticAdminDao.findByNum(id) != null){
            return true;
        }else{
            return false;
        }
    }
}