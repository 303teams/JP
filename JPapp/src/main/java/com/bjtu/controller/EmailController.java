//package com.bjtu.controller;
//
//import com.bjtu.service.MsmService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.xml.transform.Result;
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.util.concurrent.TimeUnit;
//
//@RestController
//@RequestMapping("/api/msm")
//public class EmailController {
//    @Autowired
//    MsmService msmService;
//
//    @Autowired
//    RedisTemplate redisTemplate;
//
//    //发送手机验证码
//    @GetMapping("sendEmail/{email}")
//    public Result sendEmailCode(@PathVariable String email) {
//        //从redis获取验证码，如果获取获取到，返回ok
//        // key 手机号  value 验证码
//        String code = (String) redisTemplate.opsForValue().get(email);
//        if(!StringUtils.isEmpty(code)) {
//            return Result.ok();
//        }
//        //如果从redis获取不到，
//        // 生成验证码，
//        code = msmService.getCode();
//        msmService.sendEmail(email,code);
//        redisTemplate.opsForValue().set(email,code,5, TimeUnit.MINUTES);
//        return Result.ok();
//
//    }
//}
//
