package com.hkbu.security.exception;

import com.hkbu.security.vo.ResponseBean;
import com.hkbu.security.vo.ResponseEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
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
        // 自己抛出的异常
        if (e instanceof GlobalException){
            GlobalException exception = (GlobalException) e;
            return ResponseBean.fail(exception.getResponseEnum());
        }
        // 捕获特殊的异常
        if (e instanceof BadCredentialsException){
            return ResponseBean.fail(ResponseEnum.NOT_EXIST_ERROR);
        }
        return ResponseBean.fail(ResponseEnum.FAIL);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseBean accessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        return ResponseBean.fail(ResponseEnum.REQUEST_FORBID_ERROR);
    }

}
