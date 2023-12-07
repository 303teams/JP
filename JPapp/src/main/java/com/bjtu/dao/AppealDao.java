package com.bjtu.dao;

import com.bjtu.pojo.*;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppealDao {
    public void insertAppeal(Appeal appeal);

    public List<Map<String, Object>> findAppeal(String id);

    Appeal findAPByID(Integer contentID);

    public void setAP(Integer appealID, Integer num);

    public Map<String,Object> findAPByAID(Integer appealID);
}
