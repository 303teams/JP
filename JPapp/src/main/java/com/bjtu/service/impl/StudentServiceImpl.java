package com.bjtu.service.impl;

import com.bjtu.dao.*;
import com.bjtu.exception.ServiceException;
import com.bjtu.pojo.*;
import com.bjtu.service.StudentService;
import com.bjtu.util.MathUtils;
import com.bjtu.util.TokenUtils;
import com.bjtu.util.AcountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("studentService")
public class StudentServiceImpl implements StudentService  {

    private double STD = 1.414;
    private int MIN = 3;

    @Autowired
    StudentDao studentDao;
    @Autowired
    AppealDao appealDao;
    @Autowired
    ScoreDao scoreDao;
    @Autowired
    CourseDao courseDao;

    @Autowired
    HomeworkDao homeworkDao;

    @Autowired
    ContentDao contentDao;

    @Autowired
    ScDao scDao;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    EmailService emailService;

    @Override
    public RspObject<User> login(String id, String password) {

        Student student = studentDao.findByNum(id);

        if (student == null) {
            return RspObject.fail("该学生不存在!");
        } else if (!student.getPassword().equals(password)) {
            return RspObject.fail("密码错误!");
        }  else {

            String token = TokenUtils.createToken(id.toString(),password);
            student.setToken(token);
            return RspObject.success("登录成功！",student);
        }
    }

    @Override
    public RspObject<Boolean> insert(Student student) {
        if(AcountUtils.userIsExist(student.getId())){
            return RspObject.fail("用户已存在!",Boolean.FALSE);
        }else{
            studentDao.insert(student);
            return RspObject.success("插入成功",Boolean.TRUE);
        }
    }

    @Override
    public RspObject<Boolean> deleteOne(String id) {
        if(studentDao.findByNum(id) == null){
            return RspObject.fail("用户不存在!",Boolean.FALSE);
        }else{
            studentDao.deleteByNum(id);
            return RspObject.success("删除成功！",Boolean.TRUE);
        }
    }

    @Override
    public RspObject<String> modifyEmail(String email) {
        Student student = (Student) TokenUtils.getCurrentUser();
        if(student == null){
            throw new ServiceException(500,"用户状态异常！");
        }else{
            studentDao.updateEmail(student.getId(),email);
            return RspObject.success("邮箱修改成功！新邮箱为："+ email);
        }
    }

    @Override
    public RspObject<String> modifyPassword(String newPassword, String oldPassword) {
//        根据请求上下文获取用户对象信息
        Student student = (Student) TokenUtils.getCurrentUser();
        if(student == null){
            throw new ServiceException(500,"用户不存在！");
        }else if(!student.getPassword().equals(oldPassword)) {
//            旧密码与新密码不一致
            throw new ServiceException(500,"原密码错误！");
        }else{
            student.setPassword(newPassword);
            studentDao.updatePassword(student);
//            studentDao.updatePassword(student.getId(),newPassword);
//            studentDao.deleteByNum(student.getId());
//            student.setPassword(newPassword);
//            studentDao.insert(student);
            return RspObject.success("密码修改成功!");
        }
    }

    @Override
    public RspObject<String> changePassword(String id, String password) {
        Student student = studentDao.findByNum(id);
        if(student == null){
            throw new ServiceException(500,"用户不存在！");
        }else{
            student.setPassword(password);
            studentDao.updatePassword(student);
            return RspObject.success("密码修改成功!");
        }
    }

    @Override
    public RspObject<String> modifyInfo(Student student) {
        System.out.println("modifyInfo: "+student);
        Student student1 = (Student) TokenUtils.getCurrentUser();
        if(student1 == null){
            throw new ServiceException(500,"用户不存在！");
        }else{
            student.setId(student1.getId());
            studentDao.updateInfo(student);
            return RspObject.success("信息修改成功!");
        }
    }

    public RspObject<List<Map<String, Object>>> findCourse(String id){
        try {
            List<Map<String, Object>> courses = courseDao.findSTCourse(id);

            if (courses.isEmpty()) {
                return RspObject.success("无课程信息！");
            }
            return RspObject.success("查询成功！",courses);
        } catch (Exception e) {
            throw new ServiceException(500,e.getMessage());
        }
    }

    @Override
    public RspObject<List<Homework>> findHWbyCno(String id, String cno) {
        try {
            List<Homework> contents = homeworkDao.findHWbyCno(id,cno);

            if (contents.isEmpty()) {
                return RspObject.success("无作业信息！");
            }

            return RspObject.success("查询成功！",contents);
        } catch (Exception e) {
            e.printStackTrace(); // 记录异常
            return RspObject.fail("查询失败！");
        }
    }

    @Override
    public RspObject<List<Content>> findCTsByCID(Integer contentID) {
        String id = contentDao.findSnoByCID(contentID);
        try {
            Content content = contentDao.findCTSByCID(contentID);
            Integer[] temp = content.getContents();
            List<Content> contents = new ArrayList<>();
            for(int i = 0; i<6; i++){
                contents.add(contentDao.findxCTById(temp[i],id));
            }
            return RspObject.success("查询成功！", contents);
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }


//    @Override
//    public RspObject<Boolean> setScore(Integer contentID, Integer score, String sno) {
//        try {
//            scoreDao.setCTScore(contentID,score,sno);
//            return RspObject.success("修改成功！",Boolean.TRUE);
//        }catch (Exception e){
//            throw new ServiceException(500,e.getMessage());
//        }
//    }

//    学生提交评分结果
    @Override
    public RspObject<Boolean> score(Integer contentID,Integer score,String content) {
        User user = TokenUtils.getCurrentUser();

        if(scoreDao.findSCByCIDAndSno(contentID,user.getId()) != null){
            throw new ServiceException(500,"你已评分！");
        }else {
            Score s= new Score();
            s.setSno(user.getId());
            s.setContentID(contentID);
            s.setScore(score);
            s.setContent(content);
            s.setHomeworkID(contentDao.findHIDByCID(contentID));

            long currentTimeMillis = System.currentTimeMillis();
            Timestamp currentTime = new Timestamp(currentTimeMillis);

            s.setTime(currentTime);

            try {
                scoreDao.insertScore(s);
                return RspObject.success("评分成功！",Boolean.TRUE);
            }catch (Exception e){
                throw new ServiceException(500,e.getMessage());
            }
        }

    }

    /**
     * 处理学生申诉
     * @param contentID
     * @param appealContent
     * @return
     */
    @Override
    public RspObject<Boolean> handleAppeal(Integer contentID, String appealContent) {

        int flag = 0;
        List<Score> scores = scoreDao.findSCByCID(contentID);
        MathUtils scoresfilter = new MathUtils(scores);
        User user = TokenUtils.getCurrentUser();
//        没有查重记录
        if (contentDao.findSimilarCTs(contentID).isEmpty()) {
//        获取该作业的评分情况
            while (scoresfilter.getStd() >= STD) {
                flag = 1;
                if (scoresfilter.getSize() >= MIN) {
//            删除一个异常值
                    scoresfilter.cutScore();
                } else {
                    flag = 2;
                    break;
                }
            }

        } else flag = 2;

        try{
            switch (flag) {
                case 0:
                    return RspObject.success("不符合申诉条件，拒绝处理！", Boolean.TRUE);
                case 1:
                    //执行将待置0的评分置0操作
                    List<Integer> cutList = scoresfilter.getCutIndex();
                    for (Integer i : cutList) {
                        scoreDao.setInvalid(i);
                    }
//            执行重新统计某项作业成绩的动作
                    contentDao.updateScore(contentID);
                    return RspObject.success("你的申诉已由机器自动处理", Boolean.TRUE);
                case 2:
//                将申诉交给老师处理
                    Appeal appeal = new Appeal();
                    appeal.setSno(user.getId());
                    appeal.setContentID(contentID);
                    appeal.setAppealContent(appealContent);
                    appeal.setStatus(0);
                    long currentTimeMillis = System.currentTimeMillis();
                    Timestamp currentTime = new Timestamp(currentTimeMillis);
                    appeal.setTime(currentTime);

                    appealDao.insertAppeal(appeal);
                    return RspObject.success("你的申诉符合条件，已发送至任课教师，请耐心等待。", Boolean.TRUE);
                default:
                    throw new ServiceException(500,"处理失败！");
            }
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }

    }

    @Override
    public RspObject<List<Score>> findSCByCID(Integer contentID) {
        try{
            return RspObject.success("查询成功！",scoreDao.findSCByCID(contentID));
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }


    @Override
    public RspObject<String> addStudentCourse(String id, String cno) {
        int score = 0;
        scDao.addStudentCourse(id,cno,score);
        return RspObject.success("增加选课成功！");

    }

    @Override
    public RspObject<String> deleteStudentCourse(String id, String cno) {
        scDao.deleteStudentCourse(id,cno);
        return RspObject.success("删除选课成功！");
    }

    @Override
    public RspObject<List<Course>> findUnCourse(String id) {
        try{
            return RspObject.success("查询成功！",scDao.findCourseNotSelected(id));
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }

    @Override
    public RspObject<Boolean> isSimilar(Integer contentID) {
        try{
//            查询是否有查重记录
            if(contentDao.findSimilarCTs(contentID).isEmpty()){
                return RspObject.success("无查重记录！",Boolean.FALSE);
            }else{
                return RspObject.success("有查重记录！",Boolean.TRUE);
            }
        }catch (Exception e){
            throw new ServiceException(500,e.getMessage());
        }
    }
}
