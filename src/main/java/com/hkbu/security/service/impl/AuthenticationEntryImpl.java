package com.hkbu.security.service.impl;

import com.alibaba.fastjson.JSON;
import com.hkbu.security.utils.WebUtils;
import com.hkbu.security.vo.ResponseBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryImpl implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseBean responseBean = new ResponseBean(HttpStatus.UNAUTHORIZED.value(), "用户名认证失败，请重新登录", null);
        String result = JSON.toJSONString(responseBean);
        WebUtils.renderString(response, result);
    }
}

