package com.hkbu.security.service.impl;

import com.hkbu.security.exception.GlobalException;
import com.hkbu.security.pojo.LoginUser;
import com.hkbu.security.pojo.User;
import com.hkbu.security.service.LoginService;
import com.hkbu.security.utils.JwtUtils;
import com.hkbu.security.utils.RedisCache;
import com.hkbu.security.vo.ResponseBean;
import com.hkbu.security.vo.ResponseEnum;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    RedisCache redisCache;

    @Resource
    AuthenticationManager authenticationManager;

    @Override
    public ResponseBean login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        // 调用这个方法会生成一个Authentication对象，并将UserDetail封装到Authentication的principal中
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (ObjectUtils.isEmpty(authenticate)){
            return ResponseBean.fail(ResponseEnum.LOGIN_ERROR);
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtils.createJWT(userId);
        redisCache.setCacheObject(userId + ":token", loginUser, 60, TimeUnit.MINUTES);
        return ResponseBean.success(jwt);
    }

    @Override
    public ResponseBean logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        redisCache.deleteObject(userId + ":token");
        return ResponseBean.success();
    }
}
