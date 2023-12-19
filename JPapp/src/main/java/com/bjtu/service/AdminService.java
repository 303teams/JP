package com.bjtu.service;

import com.bjtu.pojo.*;

import java.util.List;

public interface AdminService {

    public RspObject<String> addStudent(Student student);

    public RspObject<String> addTeacher(Teacher teacher);

    public RspObject<String> addCourse(Course course);

    public RspObject<String> deleteStudent(String id);

    public RspObject<String> deleteTeacher(String id);

    public RspObject<String> deleteCourse(String cno);

    public RspObject<Student> findStudent(String id);

    public RspObject<Teacher> findTeacher(String id);

    public RspObject<Course> findCourse(String cno);

    public RspObject<User> login(String id, String password);

    public RspObject<Boolean> insert(Admin admin);

    public RspObject<String> modifyEmail(String email);

    public RspObject<String> changePassword(String id, String password);

    public RspObject<String> modifyPassword(String newPassword,String oldPassword);
    public RspObject<List<Course>> findAllCourse();
    public RspObject<List<Student>> findAllStudent();
    public RspObject<List<Teacher>> findAllTeacher();

    public RspObject<Boolean> modifyCourseTeacher(String id,String cno);



}
