package com.bjtu.controller;

import com.bjtu.config.AuthAccess;
import com.bjtu.dao.AdminDao;
import com.bjtu.pojo.*;
import com.bjtu.service.AdminService;
import com.bjtu.service.StudentService;
import com.bjtu.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;


    //添加学生
    @AuthAccess

    @PostMapping("/addStudent")
    public RspObject<String> addStudent(String id,String name,String sex,Integer age,String password,String email){
        System.out.println(age);
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setSex(sex);
        student.setAge(age);
        student.setPassword(password);
        student.setEmail(email);
        return adminService.addStudent(student);
    }

    //添加教师
    @AuthAccess
    @PostMapping("/addTeacher")
    public RspObject<String> addTeacher(String id,String name,String sex,Integer age,String password,String email){

        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setName(name);
        teacher.setSex(sex);
        teacher.setAge(age);
        teacher.setPassword(password);
        teacher.setEmail(email);
        return adminService.addTeacher(teacher);
    }

    //添加课程
    @AuthAccess
    @PostMapping("/addCourse")
    public RspObject<String> addCourse(String cno,String cname){

        Course course = new Course();
        course.setCno(cno);
        course.setCname(cname);

        return adminService.addCourse(course);
    }

    //删除学生
    @AuthAccess
    @PostMapping("/deleteStudent")
    public RspObject<String> deleteStudent(String[] ids){
        return adminService.deleteStudent(ids);
    }

    //删除教师
    @AuthAccess
    @PostMapping("/deleteTeacher")
    public RspObject<String> deleteTeacher(String[] ids){
        return adminService.deleteTeacher(ids);
    }

    //删除课程
    @AuthAccess
    @PostMapping("/deleteCourse")
    public RspObject<String> deleteCourse(String[] cnos){
        return adminService.deleteCourse(cnos);
    }

    //查询学生
    @AuthAccess
    @PostMapping("/findStudent")
    public RspObject<Student> findStudent(String id){
        return adminService.findStudent(id);
    }

    //查询老师
    @AuthAccess
    @PostMapping("/findTeacher")
    public RspObject<Teacher> findTeacher(String id){
        return adminService.findTeacher(id);
    }

    //查询课程
    @AuthAccess
    @PostMapping("/findCourse")
    public RspObject<Course> findCourse(String cno){
        return adminService.findCourse(cno);
    }

    //显示所有学生
    @PostMapping("findAllStudent")
    public RspObject<List<Student>> findAllStudent(){
        return adminService.findAllStudent();
    }

    //显示所有老师
    @PostMapping("findAllTeacher")
    public RspObject<List<Teacher>> findAllTeacher(){
        return adminService.findAllTeacher();
    }

    //显示所有课程
    @PostMapping("findAllCourse")
    public RspObject<List<Course>> findAllCourse(){
        return adminService.findAllCourse();
    }

    //展示选定学生课程信息
    @PostMapping("/findStudentCourse")
    public RspObject<List<Map<String, Object>>> CourseList(String id) {

        return studentService.findCourse(id);
    }

    //展示学生未选课程
    @PostMapping("/findStudentUnCourse")
    public RspObject<List<Course>> UnCourseList(String id) {

        return studentService.findUnCourse(id);
    }
    //添加学生选课
    @PostMapping("/addStudentCourse")
    public RspObject<String> addStudentCourse(String id,String cno) {

        return studentService.addStudentCourse(id,cno);

    }

    //删除学生课程
    @PostMapping("/deleteStudentCourse")
    public RspObject<String> deleteStudentCourse(String id,String cno) {

        return studentService.deleteStudentCourse(id,cno);
    }

    //展示未教授此门课程的其他所有老师
    @PostMapping("/findTeacherUnCourse")
    public RspObject<List<Teacher>> UnTeacherList(String cno) {

        return adminService.findUnTeacher(cno);
    }

//    修改课程任教老师
    @PostMapping("/modifyCourseTeacher")
    public RspObject<String> modifyCourseTeacher(String id,String cno) {
        return adminService.modifyCourseTeacher(id,cno);
    }

    @PostMapping("/alterTeacherByID")
    public RspObject<Boolean> alterTeacherByID(String new_id,String new_name,String new_password,String new_email,String new_sex,Integer new_age){
        return adminService.alterTeacherByID(new_id,new_name,new_password,new_email,new_sex,new_age);
    }

    @PostMapping("/alterStudentByID")
    public RspObject<Boolean> alterStudentByID(String new_id,String new_name,String new_password,String new_email,String new_sex,Integer new_age){
        return adminService.alterStudentByID(new_id,new_name,new_password,new_email,new_sex,new_age);
    }
}
