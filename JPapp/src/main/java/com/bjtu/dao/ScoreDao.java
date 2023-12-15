package com.bjtu.dao;

import com.bjtu.pojo.Score;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScoreDao {

//    public void setCTScore(Integer contentID,Integer score,String sno);

    public void insertScore(Score score);

    public List<Score> findSCByCID(Integer contentID);

    public void setInvalid(Integer scoreID);
}
