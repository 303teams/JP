package com.bjtu.dao;
import com.bjtu.pojo.Content;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ContentDao {


//    根据作业ID返回该项作业
    public Content findCTById(int id);

    void insert(Content content);

//    根据作业ID返回所有提交的作业（教师端）
    public List<Content> findCTByHId(Integer homeworkID);

//    根据作业ID返回所有提交的作业的成绩（未提交按照0分处理）（学生端）
    @MapKey("sno")
    public List<Map<String,Object>> findCTscoreByHId(Integer homeworkID);

//    设置作业分数（教师端仲裁）
    public void setCTScore(Integer contentID,double score);

//    查找某学生对某作业的评分记录
    public Content findxCTById(Integer contentID,String sno);

//    根据contentID返回学生学号（学生端互评）
    public String findSnoByCID(Integer contentID);

//    根据contentid返回互评任务列表
    public Content findCTSByCID(Integer contentID);

//    根据homeworkid返回互评任务列表
    public Map<String,Object> getScoreDistribution(Integer homeworkID);

//    执行分配任务的动作
    public void deliverTask(Integer homeworkID);

//    执行计算所有分数的动作
    public void calculateAllScore(Integer homeworkID);

//    根据contentid返回homeworkid
    public Integer findHIDByCID(Integer contentID);

//   更新某项作业信息
    public void updateScore(Integer contentID);

//    @MapKey("courseID")
//    public Map<String,Map<String,Object>> getRankingByCT(Integer contentID,String sno);

//    根据homeworkId和sno找到学生提交的那份content
    public Content findCTByHIDSno(Integer homeworkID,String sno);

//    更改作业内容
    public void alterContent(Content content);

//    根据contentID查找出与之相似的作业信息
    @MapKey("contentID")
    public List<Map<String,Object>> findSimilarCTs(Integer contentID);

//    根据contentID查找出content文件内容
    public Map<String,Object> findContentByCTID(Integer contentID);

//    根据homeworkID查找出所有contentID
    public List<Integer> findCTIDByHID(Integer homeworkID);
}
