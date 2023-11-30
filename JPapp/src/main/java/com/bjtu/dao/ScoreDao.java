package com.bjtu.dao;

import com.bjtu.pojo.Score;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScoreDao {
    public void setCTScore(Integer contentID,Integer score,String sno);


    public void insertScore(Score score);




}
