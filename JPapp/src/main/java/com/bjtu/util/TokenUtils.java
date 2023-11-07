package com.bjtu.util;



import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bjtu.dao.AdminDao;
import com.bjtu.dao.StudentDao;
import com.bjtu.dao.TeacherDao;
import com.bjtu.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    private static StudentDao staticStudentDao;
    private static TeacherDao staticTeachertDao;
    private static AdminDao staticAdminDao;

    @Resource
    StudentDao studentDao;
    @Resource
    TeacherDao teacherDao;
    @Resource
    AdminDao adminDao;

    @PostConstruct
    public void setUserService() {
        staticStudentDao = studentDao;
        staticTeachertDao = teacherDao;
        staticAdminDao = adminDao;
    }

    /**
     * 生成token
     *
     * @return
     */
    public static String createToken(String userId, String sign) {
        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return user对象
     */
    public static User getCurrentUser(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                if( staticStudentDao.findByNum(Integer.parseInt(userId)) != null)
                    return staticStudentDao.findByNum(Integer.parseInt(userId));
                else if( staticTeachertDao.findByNum(Integer.parseInt(userId)) != null)
                    return staticTeachertDao.findByNum(Integer.parseInt(userId));
                else if( staticAdminDao.findByNum(Integer.parseInt(userId)) != null)
                    return staticAdminDao.findByNum(Integer.parseInt(userId));
                else return null;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
