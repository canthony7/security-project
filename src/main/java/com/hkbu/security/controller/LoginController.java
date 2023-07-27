package com.hkbu.security.controller;

import com.hkbu.security.exception.LoginDto;
import com.hkbu.security.pojo.User;
import com.hkbu.security.repository.UserRepository;
import com.hkbu.security.service.LoginService;
import com.hkbu.security.vo.ResponseBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Resource
    LoginService loginService;

    @Resource
    BCryptPasswordEncoder encoder;

    @Resource
    UserRepository userRepository;

    @PostMapping("/login")
    public ResponseBean login(@RequestBody LoginDto loginDto){
        System.out.println(loginDto.getUsername() + "---" + loginDto.getPassword());
        User user = new User();
        user.setUserName(loginDto.getUsername());
        user.setPassword(loginDto.getPassword());
        return loginService.login(user);
    }

    @PostMapping("/register")
    public ResponseBean register(@RequestBody LoginDto loginDto){
        String username = loginDto.getUsername();
        String pass = loginDto.getPassword();
        String password = encoder.encode(pass);
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        userRepository.save(user);
        return ResponseBean.success();
    }

    @PostMapping("/logout")
    public ResponseBean logout(){
        return loginService.logout();
    }

}
