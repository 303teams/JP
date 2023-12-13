package com.bjtu.util;

import com.bjtu.pojo.Score;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MathUtils {

    private List<Score> scores;
//    待置 valid = 0 的评分列表
    private List<Integer> cutIndex;

    public MathUtils(List<Score> scores){
        this.scores = new ArrayList<>();
        this.cutIndex = new ArrayList<>();
        for (Score score : scores) {
            this.scores.add(score);
        }
    }

    public double getSum(){
        double sum = 0;
        for(Score score : scores){
            sum += score.getScore();
        }
        return sum;
    }

    public double getAvg(){
        return getSum() / scores.size();
    }

    //    返回标准差
    public double getStd(){
        double avg = getAvg();
        double std = 0;
        for(Score score : scores){
            std += Math.pow(score.getScore() - avg,2);
        }
        std = Math.sqrt(std / scores.size());
        return std;
    }

    //    删除分数当中一个异常值
    public void cutScore(){
        double avg = getAvg();
        double std = getStd();
        for(Score score : scores){
            if(Math.abs(score.getScore() - avg) > 2 * std){
                scores.remove(score);
//                将该评分记录的id加入cutIndex表中
                cutIndex.add(score.getContentID());
                break;
            }
        }
    }

    public int getSize(){
        return scores.size();
    }

}
