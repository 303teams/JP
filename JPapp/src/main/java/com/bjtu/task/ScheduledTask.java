package com.bjtu.task;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.awt.Mutex;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
@EnableScheduling
@EnableAsync
public class ScheduledTask {

    public static List<Timestamp>  submitSet;
    public static List<Timestamp>  scoreSet;

    Mutex submitMutex;
    Mutex scoreMutex;

    public static boolean submitflag = true;
    public static boolean scoreflag = true;

    @PostConstruct
    public void setUserService() {
        submitMutex = new Mutex();
        scoreMutex = new Mutex();
        submitSet = new ArrayList<>();
        scoreSet = new ArrayList<>();
    }

//    扫描submitSet表，执行提交截止时间到达后的动作
    @Async
    @Scheduled(cron = "*/1 * * * * ?")
    public void submitScan() throws InterruptedException {
        if (submitflag) {
            submitMutex.lock();
            for (int i = 0; i < submitSet.size(); i++) {
                if (submitSet.get(i).getTime() < new Timestamp(System.currentTimeMillis()).getTime()) {
                    System.out.println("执行提交截止时间到达后的动作!");
                    submitSet.remove(i);
                }
            }
            submitMutex.unlock();
        }
    }


    //    扫描scoreSet表，执行评分截止时间到达后的动作
    @Async
    @Scheduled(cron = "*/1 * * * * ?")
    public void scoreScan() throws InterruptedException {
        if(scoreflag){
            for(int i = 0; i<scoreSet.size(); i++){
//                对scoreSet上锁
                scoreMutex.lock();
                if(scoreSet.get(i).getTime() < new Timestamp(System.currentTimeMillis()).getTime()){
                    System.out.println("执行分数统计操作! "+scoreSet.get(i));
                    scoreSet.remove(i);
                }
//                对scoreSet解锁
                scoreMutex.unlock();
            }
        }
    }



}
