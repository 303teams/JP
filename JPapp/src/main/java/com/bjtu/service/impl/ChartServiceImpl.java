package com.bjtu.service.impl;

import com.bjtu.dao.ContentDao;
import com.bjtu.dao.HomeworkDao;
import com.bjtu.dao.ScDao;
import com.bjtu.exception.ServiceException;
import com.bjtu.pojo.Homework;
import com.bjtu.pojo.RspObject;
import com.bjtu.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service("ChartService")
public class ChartServiceImpl implements ChartService {

    @Autowired
    ContentDao contentDao;
    @Autowired
    HomeworkDao homeworkDao;
    @Autowired
    ScDao scDao;

    public RspObject<Map<String,Object>> getScoreDistribution(Integer homeworkID){
        try{
            return RspObject.success("分数分布查询成功！",
                    contentDao.getScoreDistribution(homeworkID));
        }catch(Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }

//    返回一个学生每项课程的统计信息
//   {
//      cno = 1011
//      cname = C++
//      score = 5
//      ranks = [1,16,7]
//    }
    @Override
    public RspObject<List<Map<String, Object>>> getAllRanking(String id) {
        List data = new ArrayList<Map<String,Object>>();
        List<Homework> homeworkList;
        List<Map<String,Object>> scores;
        try{
//           获取学生课程列表（返回信息包括每项课程的课程序号，课程名，课程成绩）
            List<Map<String,Object>> courseList = scDao.findCourseInfoBySno(id);
            for(Map course:courseList){
//                根据课程序号获得某课程的作业ID列表
                homeworkList = homeworkDao.findHWInfoByCno(course.get("cno").toString());
                List<Integer> ranks = new ArrayList<>();
                for(Homework homework:homeworkList){
//                    筛选已经过了截至时间的的作业
                    if(homework.getScoreDdl().getTime() < new Timestamp(System.currentTimeMillis()).getTime()){
                        //根据作业ID获得某项作业全班的得分情况
                        scores = contentDao.findCTscoreByHId(homework.getHomeworkID());
                        System.out.println(scores);
//                    获得本学生排名数据
                        ranks.add(getRank(scores,id));
                    }
                }
//                将该排名数据加入至course中
                course.put("ranks",ranks);
//                将一个课程的数据加入之返回体中
                data.add(course);
            }
            System.out.println(data);
            return RspObject.success("学生成绩查询成功！",data);
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }

    // 返回一个学生每项课程的的每项作业的成绩排名百分比数据
    private static int getRank(List<Map<String, Object>> scores, String sno) {
        // 排序成绩表（降序）
        Collections.sort(scores, (s1, s2) -> Double.compare((Double) s2.get("score"), (Double) s1.get("score")));

        int rank = 0;
        for (int i = 0; i < scores.size(); i++) {
            if (sno.equals(scores.get(i).get("sno").toString())) {
                // 学号匹配，返回排名（从1开始）
                rank = i + 1;
                break;
            }
        }
        return rank;
    }

    public static void main(String[] args) {
        List<Map<String, Object>> scores = Arrays.asList(
                new HashMap<String, Object>() {{ put("score", 80.0); put("sno", "21301001"); }},
                new HashMap<String, Object>() {{ put("score", 90.0); put("sno", "21301002"); }},
                new HashMap<String, Object>() {{ put("score", 75.0); put("sno", "21301003"); }}
                // 添加更多成绩数据
        );

        String snoToFind = "21301001";
        int rank = getRank(scores, snoToFind);
        System.out.println("学号为 " + snoToFind + " 的学生排名为：" + rank);
    }

}
