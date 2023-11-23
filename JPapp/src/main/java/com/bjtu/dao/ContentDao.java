package com.bjtu.dao;
import com.bjtu.pojo.Content;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContentDao {


    public Content findCTById(Integer id);
    void insert(Content content);

    public List<Content> findCTByHId(Integer homeworkID);

    public void setCTScore(Integer contentID,Integer score);

}
