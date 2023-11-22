package com.bjtu.dao;

import com.bjtu.pojo.Homework;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HomeworkDao {
    public List<Homework> findAll();

//    public List<Homework> findById(String id,String cno);

//    public List<Homework> findByTeaId(String id,String cno);

    public Homework findHWbyId(String id);

    void insert(Homework homework);

}
