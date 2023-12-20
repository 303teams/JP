package com.bjtu.task;
import com.bjtu.dao.ContentDao;
import com.bjtu.dao.HomeworkDao;
import com.bjtu.dao.ScoreDao;
import com.bjtu.dao.SimilarityDao;
import com.bjtu.pojo.Content;
import com.bjtu.pojo.Homework;
import com.bjtu.util.FileUtils;
import com.bjtu.util.SimHashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@EnableScheduling
@EnableAsync
public class ScheduledTask {

    private static List<Homework>  submitSet;
    private static List<Homework>  scoreSet;

    private static Lock submitLock;
    private static Lock scoreLock;

    private static boolean submitflag = true;
    private static boolean scoreflag = true;

//    最高可接受查重率
    private double MAX_SIMILARITY = 0.8;

    @Autowired
    ContentDao contentDao;

    @Autowired
    HomeworkDao homeworkDao;

    @Autowired
    FileUtils fileUtils;

    @Autowired
    SimilarityDao similarityDao;

    @Autowired
    ScoreDao scoreDao;

    @PostConstruct
    public void setUserService() {
        submitLock = new ReentrantLock();
        scoreLock = new ReentrantLock();
        submitSet = new ArrayList<>();
        scoreSet = new ArrayList<>();

//        将未到达截止时间的作业的id导入之submitSet表和scoreSet表;
        List<Homework> homeworks = homeworkDao.findSimpleAll();
        for(Homework homework : homeworks){
            if(homework.getSubmitDdl().getTime() > new Timestamp(System.currentTimeMillis()).getTime()){
                submitSet.add(homework);
            }
            if(homework.getScoreDdl().getTime() > new Timestamp(System.currentTimeMillis()).getTime()){
                scoreSet.add(homework);
            }
        }
    }

//    扫描submitSet表，执行提交截止时间到达后的动作
    @Async
    @Scheduled(cron = "*/1 * * * * ?")
    public void submitScan() throws InterruptedException {
        if (submitflag) {

            submitLock.lock();
            for (int i = 0; i < submitSet.size(); i++) {
                if (submitSet.get(i).getSubmitDdl().getTime() < new Timestamp(System.currentTimeMillis()).getTime()) {
                    Integer homeworkID = submitSet.get(i).getHomeworkID();
                    System.out.println("分配互评任务:"+homeworkID.toString()+" "+new Timestamp(System.currentTimeMillis()));
                    contentDao.deliverTask(homeworkID);
                    submitSet.remove(i);
                }
            }
            submitLock.unlock();
        }
    }

    //    扫描scoreSet表，执行评分截止时间到达后的动作
    @Async
    @Scheduled(cron = "*/1 * * * * ?")
    public void scoreScan() throws InterruptedException {
        if(scoreflag){
            for(int i = 0; i<scoreSet.size(); i++){
//                对scoreSet上锁
                scoreLock.lock();
                if(scoreSet.get(i).getScoreDdl().getTime() < new Timestamp(System.currentTimeMillis()).getTime()){
                    Integer homeworkID = scoreSet.get(i).getHomeworkID();
                    System.out.println("分数统计 "+ homeworkID.toString()+" "+new Timestamp(System.currentTimeMillis()));
//                    1.进行查重
                    PlagiarismCheck(homeworkID);
//                    2.分数统计
                    contentDao.calculateAllScore(homeworkID);
                    scoreSet.remove(i);
                }
//                对scoreSet解锁
                scoreLock.unlock();
            }
        }
    }

    public void addHomework(Integer homeworkID){
        addScoreSet(homeworkID);
        addSubmitSet(homeworkID);
    }

    private void addSubmitSet(Integer homeworkID){
        submitLock.lock();
        Homework homework = homeworkDao.findSimpleHWById(homeworkID);
        submitSet.add(homework);
        submitLock.unlock();
    }

    private void addScoreSet(Integer homeworkID){
        scoreLock.lock();
        Homework homework = homeworkDao.findSimpleHWById(homeworkID);
        scoreSet.add(homework);
        scoreLock.unlock();
    }

    public void alterSubmitDdl(Integer homeworkID,String submitDdl) throws ParseException {
        submitLock.lock();
        int i = 0;
        for(; i<submitSet.size(); i++){
            if(submitSet.get(i).getHomeworkID() == homeworkID){
                submitSet.get(i).setSubmitDdl(transfer(submitDdl));
                break;
            }
        }
//        找不到
        if(i == submitSet.size()){
            Homework homework = new Homework()
                    .setHomeworkID(homeworkID)
                    .setSubmitDdl(transfer(submitDdl));
            submitSet.add(homework);
        }
        submitLock.unlock();
    }

    public void alterScoreDdl(Integer homeworkID,String scoreDdl) throws ParseException {
        scoreLock.lock();
        int i = 0;
        for(; i<scoreSet.size(); i++){
            if(scoreSet.get(i).getHomeworkID() == homeworkID){
                scoreSet.get(i).setScoreDdl(transfer(scoreDdl));
                break;
            }
        }
//        找不到
        if(i == scoreSet.size()){
            Homework homework = new Homework()
                    .setHomeworkID(homeworkID)
                    .setScoreDdl(transfer(scoreDdl));
            scoreSet.add(homework);
        }
        scoreLock.unlock();
    }

//    删除一个作业
    public void deleteHW(Integer homeworkID){
        submitLock.lock();
        for(int i = 0;i<submitSet.size(); i++){
            if(submitSet.get(i).getHomeworkID() == homeworkID){
                submitSet.remove(i);
                break;
            }
        }
        submitLock.unlock();
        scoreLock.lock();
        for(int i = 0;i<scoreSet.size(); i++){
            if(scoreSet.get(i).getHomeworkID() == homeworkID){
                scoreSet.remove(i);
                break;
            }
        }
        scoreLock.unlock();
    }

    private Timestamp transfer(String time) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new Timestamp(dateFormat.parse(time).getTime());
    }

//    查重任务
    private void PlagiarismCheck(Integer homeworkID){
        List<Integer> cts = contentDao.findCTIDByHID(homeworkID);
        Map<Integer,SimHashUtil> contents = new HashMap<>();
        Map<String,Object> temp;
        for(Integer CTid:cts){
            temp = contentDao.findContentByCTID(CTid);
            contents.put(CTid,new SimHashUtil(fileUtils.transToString(
                    (byte[])temp.get("content"),
                    (String)temp.get("fileName")),64));
        }
        int size = contents.size();
        Integer i = 0,j = 0;
        double s = 0;
        Integer id_i,id_j;
        for(; i < size; i++){
            for(j = i+1; j < size; j++){
//                获取汉明相似度
                id_i = cts.get(i);
                id_j = cts.get(j);
                s = contents.get(id_i).getSimilar(contents.get(id_j));
                if(s > MAX_SIMILARITY) {
//                    若有一对作业查重率超标
//                    1.添加查重记录
                    similarityDao.addOne(id_i,id_j,s);
                    similarityDao.addOne(id_j,id_i,s);
//                    2.将两份作业的评分记录设为无效
                    scoreDao.setInvalidByCID(id_i,id_j);
                }
            }
        }
    }

    public static void main(String[] args) {

    }

}