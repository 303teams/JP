package com.bjtu.dao;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ScDao {

    List<Integer> findCoursesBySno(String sno);

    @MapKey("cno")
    Map<String,Map<String,Object>> getFinalScoresBySno(String sno);

}
