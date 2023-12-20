package com.bjtu.controller;

import com.bjtu.config.AuthAccess;
import com.bjtu.pojo.*;
import com.bjtu.service.ContentService;
import com.bjtu.service.HomeworkService;
import com.bjtu.service.TeacherService;
import com.bjtu.util.TokenUtils;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;
    @Autowired
    HomeworkService homeworkService;
    @Autowired
    ContentService contentService;


    @PostMapping("modifyInfo")
    public RspObject<String> modifyInfo(Teacher teacher){
        return teacherService.modifyInfo(teacher);
    }

//    教师查看自己的课程列表
    @PostMapping("/findCourse")
    public RspObject<List<Map<String, Object>>> CourseList() {
        User user = TokenUtils.getCurrentUser();
        return teacherService.findCourse(user.getId());
    }

//    教师查看本课程布置的所有作业的列表
    @PostMapping("/findHWbyCno")
    public RspObject<List<Homework>> findHWbyCno(@RequestParam String cno) {
        return teacherService.findHWbyCno(cno);
    }

//    教师点击某课程的某作业得到班级名单
    @PostMapping("/findCTByHId")
    public RspObject<List<Content>> findCTByHId(@RequestParam Integer homeworkID){
        return teacherService.findCTByHId(homeworkID);
    }

//    教师修改学生分数
    @PostMapping("/setCTScore")
    public RspObject<Boolean> setCTScore(@RequestParam Integer contentID,Double score){
            return teacherService.setCTScore(contentID, score);
    }

    //教师查看学生申诉信息
    @PostMapping("/findAppeal")
    public RspObject<List<Map<String, Object>>> findAppeal(){
        User user = TokenUtils.getCurrentUser();
        return teacherService.findAppeal(user.getId());
    }

    //教师点击申诉信息,改变staus值：0-1
    @PostMapping("/ClickAppeal")
    public RspObject<Boolean> ClickAppeal(Integer appealID){
        Appeal appeal = new Appeal();
        appeal = teacherService.findAPByID(appealID);

        if(appeal.getStatus()==0){
            return teacherService.setAP(appealID,1);
        }
        else return RspObject.success("已处理！");
    }

    //    申诉后教师修改学生分数
    @PostMapping("/changeCTScore")
    public RspObject<Boolean> changeCTScore(Integer appealID,Integer contentID,Integer score){
        teacherService.setAP(appealID,2);
        return teacherService.setCTWeightedScore(contentID,score);
    }

    @PostMapping("/findAPByAID")
    public RspObject<Map<String,Object>> findAPByAID(Integer appealID){
        return teacherService.findAPByAID(appealID);
    }

    @PostMapping("/deleteAPByAID")
    public RspObject<Boolean> deleteAPByAID(Integer appealID){
        return teacherService.deleteAPByAID(appealID);
    }

    @PostMapping("/deleteHWByHId")
    public RspObject<Boolean> deleteHWByHId(Integer homeworkID){
        return homeworkService.deleteByHId(homeworkID);
    }

    @PostMapping("/alterDdlByHID")
    public RspObject<Boolean> alterDdlByHID(Integer homeworkID,String submitDdl,String scoreDdl){
        return homeworkService.alterDdlByHID(homeworkID,submitDdl,scoreDdl);
    }
//    教师端返回与该学生作业相同的其他学生的学号列表
    @PostMapping("/findSimilarCTs")
    public RspObject<List<Map<String,Object>>> findSimilarCTs(Integer contentID){
        return teacherService.findSimilarCTs(contentID);
    }

}
