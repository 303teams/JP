package com.bjtu.dao;

import com.bjtu.pojo.RspObject;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ScDao {

    @MapKey("")
    List<Map<String,Object>> findCourseInfoBySno(String sno);

    RspObject<Boolean> addStudentCourse(String id, String cno,int score);

    RspObject<Boolean> deleteStudentCourse(String id, String cno);
}
