package com.bjtu.dao;
import com.bjtu.pojo.Content;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContentDao {


    public Content findCTById(int id);

    void insert(Content content);

    public List<Content> findCTByHId(Integer homeworkID);

    public void setCTScore(Integer contentID,Integer score);

    public Content findxCTById(Integer contentID,String sno);

    public String findSnoByCID(Integer contentID);

    public Content findCTSByCID(Integer contentID);

}
