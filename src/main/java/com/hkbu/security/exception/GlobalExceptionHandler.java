package com.hkbu.security.exception;

import com.hkbu.security.vo.ResponseBean;
import com.hkbu.security.vo.ResponseEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Chet
 * @date 11/2/2023 3:42 pm
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseBean ExceptionHandler(Exception e){
        if (e instanceof GlobalException){
            GlobalException exception = (GlobalException) e;
            return ResponseBean.fail(exception.getResponseEnum());
        }
        return ResponseBean.fail(ResponseEnum.FAIL);
    }
}
