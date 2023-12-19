package com.bjtu.controller;

import com.bjtu.pojo.Course;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.Student;
import com.bjtu.pojo.Teacher;
import com.bjtu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    //添加学生
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
    @PostMapping("/addCourse")
    public RspObject<String> addCourse(String cno,String cname){

        Course course = new Course();
        course.setCno(cno);
        course.setCname(cname);

        return adminService.addCourse(course);
    }

    //删除学生
    @PostMapping("/deleteStudent")
    public RspObject<String> deleteStudent(String id){
        return adminService.deleteStudent(id);
    }

    //删除教师
    @PostMapping("/deleteTeacher")
    public RspObject<String> deleteTeacher(String id){
        return adminService.deleteTeacher(id);
    }

    //删除课程
    @PostMapping("/deleteCourse")
    public RspObject<String> deleteCourse(String cno){
        return adminService.deleteCourse(cno);
    }

    //查询学生
    @PostMapping("/findStudent")
    public RspObject<Student> findStudent(String id){
        return adminService.findStudent(id);
    }

    //查询老师
    @PostMapping("/findTeacher")
    public RspObject<Teacher> findTeacher(String id){
        return adminService.findTeacher(id);
    }

    //查询课程
    @PostMapping("/findCourse")
    public RspObject<Course> findCourse(String cno){
        return adminService.findCourse(cno);
    }

}
