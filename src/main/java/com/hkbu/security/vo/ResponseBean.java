package com.hkbu.security.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Chet
 * @date 10/2/2023 3:02 am
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBean {

    private int code;
    private String message;
    private Object data;

    public static ResponseBean success(){
        return new ResponseBean(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMessage(), null);
    }
    public static ResponseBean success(Object o){
        return new ResponseBean(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMessage(), o);
    }
    public static ResponseBean fail(ResponseEnum responseEnum){
        return new ResponseBean(responseEnum.getCode(), responseEnum.getMessage(), null);
    }
    public static ResponseBean fail(ResponseEnum responseEnum, Object o){
        return new ResponseBean(responseEnum.getCode(), responseEnum.getMessage(), o);
    }
}
