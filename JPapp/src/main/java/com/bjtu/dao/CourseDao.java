package com.bjtu.dao;

import com.bjtu.pojo.Course;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.Teacher;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CourseDao {

    //    查询教师课程
    @MapKey("contentID")
    public List<Map<String, Object>> findTHCourse(String id);

//    查询学生课程
    @MapKey("contentID")
    public List<Map<String, Object>> findSTCourse(String id);

    public void updateCourseTno(String id,String cno);

    List<Teacher> findUnTeacher(String cno);
}
