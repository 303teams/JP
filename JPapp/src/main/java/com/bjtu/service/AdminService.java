package com.bjtu.service;

import com.bjtu.pojo.*;

import java.util.List;

public interface AdminService {

    public RspObject<String> addStudent(Student student);

    public RspObject<String> addTeacher(Teacher teacher);

    public RspObject<String> addCourse(Course course);

    public RspObject<String> deleteStudent(String[] ids);

    public RspObject<String> deleteTeacher(String[] id);

    public RspObject<String> deleteCourse(String[] cno);

    public RspObject<String> reviveStudent(String id);

    public RspObject<String> reviveTeacher(String id);

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

    public RspObject<String> modifyCourseTeacher(String id,String cno);

    public RspObject<List<Teacher>> findUnTeacher(String cno);


    public RspObject<Boolean> alterTeacherByID(String new_id,String new_name,String new_password,String new_email,String new_sex,Integer new_age);

    public RspObject<Boolean> alterStudentByID(String new_id,String new_name,String new_password,String new_email,String new_sex,Integer new_age);

}