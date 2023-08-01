package com.hkbu.security.service.impl;

import com.alibaba.fastjson.JSON;
import com.hkbu.security.utils.WebUtils;
import com.hkbu.security.vo.ResponseBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseBean responseBean = new ResponseBean(HttpStatus.FORBIDDEN.value(), "您的权限不足", null);
        String result = JSON.toJSONString(responseBean);
        System.out.println(result);
        WebUtils.renderString(response, result);
    }
}
