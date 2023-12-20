package com.bjtu.service;

import cn.hutool.extra.mail.MailUtil;
import com.bjtu.dao.StudentDao;
import com.bjtu.pojo.Homework;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.Student;
import com.bjtu.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HomeworkService {

    RspObject<List<Homework>> findAll();


//    返回一个课程下的所有学生的作业
    Homework findHWById(Integer id);

    void addHomework(Homework homework);

    RspObject<Boolean> deleteByHId(Integer homeworkID);

    RspObject<Boolean> setAnswer(Integer homeworkID,byte[] answer,String Afilename);

    public RspObject<Boolean> alterDdlByHID(Integer homeworkID, String submitDdl,String scoreDdl);

    public RspObject<Object> alterHWByHID(Integer homeworkID, MultipartFile file,String info);

}