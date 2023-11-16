package com.bjtu.dao;
import com.bjtu.pojo.Content;
import com.bjtu.pojo.Homework;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContentDao {


    public Content findContentById(String id);
    void insert(Content content);
}
