package com.bjtu.task;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import sun.awt.Mutex;

import java.sql.Timestamp;
import java.util.List;

@Component
//@EnableScheduling
@EnableAsync
public class ScheduledTask {

    public static List<Timestamp>  submitSet;
    public static List<Timestamp>  scoreSet;

    Mutex submitMutex;
    Mutex scoreMutex;

//    扫描submitSet表，执行提交截止时间到达后的动作
//    @Async
    public void submitScan() {

    }

    //    扫描scoreSet表，执行评分截止时间到达后的动作
//    @Async
    public void scoreScan() throws InterruptedException {
        while(true){
            for(int i = 0; i<scoreSet.size(); i++){
//                对scoreSet上锁
                scoreMutex.lock();
                if(scoreSet.get(i).getTime()>new Timestamp(System.currentTimeMillis()).getTime()){
                    System.out.println("执行分数统计操作!");
                    scoreSet.remove(i);
                }
//                对scoreSet解锁
                scoreMutex.unlock();
            }
//            设置0.5s扫描一次
            Thread.sleep(500);
        }
    }



}
