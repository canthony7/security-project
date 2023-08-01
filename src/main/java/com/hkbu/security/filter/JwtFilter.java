package com.hkbu.security.filter;

import com.hkbu.security.exception.GlobalException;
import com.hkbu.security.pojo.LoginUser;
import com.hkbu.security.utils.JwtUtils;
import com.hkbu.security.utils.RedisCache;
import com.hkbu.security.vo.ResponseEnum;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Resource
    RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        // 如果token为空则放行，让其访问匿名资源
        if (token == null){
            filterChain.doFilter(request, response);
            // 必须要return，否则无论token是否为空都会执行后面的代码
            return;
        }
        // 如果token不为空，判断token是否有效
        String userId;
        try {
            Claims claims = JwtUtils.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            // token无效：抛出异常
            throw new GlobalException(ResponseEnum.ILLEGAL_TOKEN_ERROR);
        }

        // token有效：封装authentication添加到SecurityContextHolder中，这代表通过认证
        LoginUser loginUser = redisCache.getCacheObject(userId + ":token");
        if (loginUser == null){
            throw new GlobalException(ResponseEnum.TOKEN_ERROR);
        }
        logger.info(loginUser);

        // 自己通过Redis为SecurityContextHolder对象时，要手动把权限信息加进入
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        redisCache.setCacheObject(userId + ":token", loginUser, 60, TimeUnit.MINUTES);
        filterChain.doFilter(request, response);
    }
}
