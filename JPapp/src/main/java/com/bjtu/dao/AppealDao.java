package com.bjtu.dao;

import com.bjtu.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppealDao {
    public void insertAppeal(Appeal appeal);

    public List<Map<String, Object>> findAppeal(String id);

    public List<Map<String, Object>> findAppealByCno(String cno);


    Appeal findAPByID(int contentID);


    public void setAP(@Param("contentID") int contentID, @Param("num") int num);
}
