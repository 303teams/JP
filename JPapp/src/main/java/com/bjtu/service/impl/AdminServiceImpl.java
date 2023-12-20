package com.bjtu.service.impl;

import com.bjtu.dao.AdminDao;
import com.bjtu.dao.CourseDao;
import com.bjtu.exception.ServiceException;
import com.bjtu.pojo.*;
import com.bjtu.service.AdminService;
import com.bjtu.util.TokenUtils;
import com.bjtu.util.AcountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;
    @Autowired
    CourseDao courseDao;

    @Override
    public RspObject<String> addStudent(Student student) {

        if(AcountUtils.userIsExist(student.getId())){
            return RspObject.fail("用户已存在!");
        }
        adminDao.insertStudent(student);
        return RspObject.success("成功插入学生！");
    }

    @Override
    public RspObject<String> addTeacher(Teacher teacher) {

        if(AcountUtils.userIsExist(teacher.getId())){
            return RspObject.fail("用户已存在!");
        }
        adminDao.insertTeacher(teacher);
        return RspObject.success("成功插入老师！");
    }
    @Override
    public RspObject<String> addCourse(Course course) {

        adminDao.insertCourse(course);
        return RspObject.success("成功插入课程！");
    }

    @Override
    public RspObject<String> deleteStudent(String[] ids) {
        for(String id:ids){
            adminDao.deleteStudentByID(id);
        }
        return RspObject.success("成功删除学生！");
    }
    @Override

    public RspObject<String> deleteTeacher(String[] ids) {
        for(String id:ids){
            adminDao.deleteTeacherByID(id);
        }
        return RspObject.success("成功删除老师！");
    }

    @Override
    public RspObject<String> deleteCourse(String[] cnos) {
        for(String cno:cnos){
            adminDao.deleteCourseByCno(cno);
        }
        return RspObject.success("成功删除课程！");
    }

    @Override
    public RspObject<Student> findStudent(String id) {
        Student student = adminDao.findStudentByID(id);
        if(student == null){
            return RspObject.fail("该学生不存在！");
        }else{
            return RspObject.success("查询成功！",student);
        }
    }

    @Override
    public RspObject<Teacher> findTeacher(String id) {
        Teacher teacher = adminDao.findTeacherByID(id);
        if(teacher == null){
            return RspObject.fail("该老师不存在！");
        }else{
            return RspObject.success("查询成功！",teacher);
        }
    }

    @Override
    public RspObject<Course> findCourse(String cno) {
        Course course = adminDao.findCourseByCno(cno);
        if(course == null){
            return RspObject.fail("该课程不存在！");
        }else{
            return RspObject.success("查询成功！",course);
        }
    }

    @Override
    public RspObject<List<Course>> findAllCourse() {
        List<Course> course = adminDao.findAllCourse();
        if(course == null){
            return RspObject.fail("不存在课程！");
        }else{
            return RspObject.success("查询成功！",course);
        }
    }

    @Override
    public RspObject<List<Student>> findAllStudent() {
        List<Student> student = adminDao.findAllStudent();
        if(student == null){
            return RspObject.fail("不存在学生！");
        }else{
            return RspObject.success("查询成功！",student);
        }
    }

    @Override
    public RspObject<List<Teacher>> findUnTeacher(String cno) {
        List<Teacher> teacher = courseDao.findUnTeacher(cno);
        if(teacher == null){
            return RspObject.fail("不存在！");
        }else{
            return RspObject.success("查询成功！",teacher);
        }
    }

    @Override
    public RspObject<String> modifyCourseTeacher(String id,String cno) {
        courseDao.updateCourseTno(id,cno);
        return RspObject.success("修改成功！");
    }

    @Override
    public RspObject<List<Teacher>> findAllTeacher() {
        List<Teacher> teacher = adminDao.findAllTeacher();
        if(teacher == null){
            return RspObject.fail("不存在老师！");
        }else{
            return RspObject.success("查询成功！",teacher);
        }
    }

    @Override
    public RspObject<User> login(String id, String password) {
        Admin admin = adminDao.findByNum(id);
//        System.out.println(admin);
        if (admin == null) {
            return RspObject.fail("该管理员不存在!");
        } else if (!admin.getPassword().equals(password)) {
            return RspObject.fail("密码错误!");
        } else {
            String token = TokenUtils.createToken(id.toString(),password);
            admin.setToken(token);
            return RspObject.success("登录成功！",admin);
        }
    }

    @Override
    public RspObject<Boolean> insert(Admin admin) {
        if(AcountUtils.userIsExist(admin.getId())){
            return RspObject.fail("用户已存在!",Boolean.FALSE);
        }else{
            adminDao.insert(admin);
            return RspObject.success("插入成功",Boolean.TRUE);
        }
    }




    @Override
    public RspObject<String> modifyEmail(String email) {
        Admin admin = (Admin) TokenUtils.getCurrentUser();
        if(admin == null){
            throw new ServiceException(500,"用户不存在！");
        }else{
            adminDao.updateEmail(admin.getId(),email);
            return RspObject.success("密码修改成功！");
        }
    }

    @Override
    public RspObject<String> changePassword(String id, String password) {
        Admin admin = adminDao.findByNum(id);
        if(admin == null){
            throw new ServiceException(500,"用户不存在！");
        }else{
//            adminDao.updatePassword(id,password);
            adminDao.deleteByNum(admin.getId());
            admin.setPassword(password);
            adminDao.insert(admin);
            return RspObject.success("密码修改成功！");
        }
    }

    @Override
    public RspObject<String> modifyPassword(String newPassword,String oldPassword) {
        Admin admin = (Admin) TokenUtils.getCurrentUser();
        if(admin == null){
            throw new ServiceException(500,"用户不存在！");
        }else if(!admin.getPassword().equals(oldPassword)){
            System.out.println("原密码错误！");
            throw new ServiceException(500,"原密码错误！");
        }else{
//            adminDao.updatePassword(admin.getId(),newPassword);
            adminDao.deleteByNum(admin.getId());
            admin.setPassword(newPassword);
            adminDao.insert(admin);
            return RspObject.success("密码修改成功！");

        }
    }

}
