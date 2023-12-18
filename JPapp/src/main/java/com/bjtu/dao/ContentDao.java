package com.bjtu.dao;
import com.bjtu.pojo.Content;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ContentDao {


    public Content findCTById(int id);

    void insert(Content content);

//    根据作业ID返回所有提交的作业（教师端）
    public List<Content> findCTByHId(Integer homeworkID);

//    根据作业ID返回所有提交的作业的成绩（未提交按照0分处理）（学生端）
    @MapKey("sno")
    public List<Map<String,Object>> findCTscoreByHId(Integer homeworkID);

    public void setCTScore(Integer contentID,Integer score);

    public Content findxCTById(Integer contentID,String sno);

    public String findSnoByCID(Integer contentID);

    public Content findCTSByCID(Integer contentID);

    public Map<String,Object> getScoreDistribution(Integer home);

    public void deliverTask(Integer homeworkID);

    public void calculateAllScore(Integer homeworkID);

    public Integer findHIDByCID(Integer contentID);

    public void updateScore(Integer contentID);

    @MapKey("courseID")
    public Map<String,Map<String,Object>> getRankingByCT(Integer contentID,String sno);

    public Content findCTByHIDSno(Integer homeworkID,String sno);

    public void alterContent(Content content);
}
