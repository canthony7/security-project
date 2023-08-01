package com.hkbu.security.config;

import com.hkbu.security.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author Chet
 * @date 2022/10/25 17:34
 * 新api
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Resource
    private AuthenticationConfiguration authenticationConfiguration;

    @Resource
    private JwtFilter jwtFilter;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    /**
     * 指定加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        // 使用官方推荐的BCrypt加密密码，也就是md5加随机盐
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * configure(WebSecurity)用于影响全局安全性(配置资源，设置调试模式，通过实现自定义防火墙定义拒绝请求)的配置设置。
     * 一般用于配置全局的某些通用事物，例如静态资源等
     * 新版本其实不推荐把路径的拦截写在这里，而是推荐写在securityFilterChain里面
     * "You are asking Spring Security to ignore Ant [pattern='/resources/**']. This is not recommended -- please use permitAll via HttpSecurity#authorizeHttpRequests instead"
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/resources/**", "/ignore2");
    }

    /**
     * 配置接口拦截
     * configure(HttpSecurity)允许基于选择匹配在资源级配置基于网络的安全性，也就是对角色所能访问的接口做出限制
     * @param
     * @return HttpSecurity
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler)
                .and()
        // 允许跨域
                .cors()
                .and()
                // 配置路径是否需要认证
                .authorizeRequests()
                // 允许匿名访问
                .antMatchers("/user/login", "/user/register").permitAll()
                // 配置权限
                .antMatchers("/test/any", "/user/logout", "/test/funny").authenticated()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}


