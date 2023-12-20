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
    public RspObject<String> addStudent(String id,String name,String sex,String password){

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setSex(sex);
        student.setPassword(password);
        return adminService.addStudent(student);
    }

    //添加教师
    @AuthAccess
    @PostMapping("/addTeacher")
    public RspObject<String> addTeacher(String id,String name,String sex,String password){

        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setName(name);
        teacher.setSex(sex);
        teacher.setPassword(password);
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
//    @PostMapping("/findStudentUnCourse")
//    public RspObject<List<Map<String, Object>>> UnCourseList(String id) {
//
//        return studentService.findUnCourse(id);
//    }
    //添加学生选课
    @PostMapping("/addStudentCourse")
    public RspObject<Boolean> addStudentCourse(String id,String cno) {

        return studentService.addStudentCourse(id,cno);
    }

    //删除学生课程
    @PostMapping("/deleteStudentCourse")
    public RspObject<Boolean> deleteStudentCourse(String id,String cno) {

        return studentService.deleteStudentCourse(id,cno);
    }

//    修改课程任教老师
    @PostMapping("/modifyCourseTeacher")
    public RspObject<Boolean> modifyCourseTeacher(String id,String cno) {

        return adminService.modifyCourseTeacher(id,cno);
    }
}
