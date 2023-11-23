package com.bjtu.dao;

import com.bjtu.pojo.Course;
import com.bjtu.pojo.Homework;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.Student;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentDao {
    //    插入
    public void insert(Student student);
    //    查询
    public List<Student> findAll();
    public Student findByNum(String id);
    public void deleteByNum(String id);
    public void updateInfo(Student student);
    public void updatePassword(Student student);
    public void updateEmail(String id, String email);


//    @MapKey("cno")
    public List<Map<String, Object>> findCourse(String id);

//    学生查看某项课程的作业列表
    public List<Homework> findHWbyCno(String id,String cno);

}
