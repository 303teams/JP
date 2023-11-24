package com.bjtu.exception;

import com.bjtu.pojo.RspObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionMaster {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public RspObject serviceException(ServiceException e) {
        return RspObject.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RspObject exception(Exception e) {
        return RspObject.fail(e.getMessage());
    }

}