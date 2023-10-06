package com.bjtu.config;

import com.bjtu.pojo.RspObject;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionMaster {

//    表示处理所有异常
    @ExceptionHandler(Exception.class)
    public RspObject<String> handler(Exception e){
        return RspObject.fail(e.getMessage());
    }
}
