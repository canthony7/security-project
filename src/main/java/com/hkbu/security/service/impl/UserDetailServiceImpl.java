package com.hkbu.security.service.impl;

import com.hkbu.security.exception.GlobalException;
import com.hkbu.security.pojo.LoginUser;
import com.hkbu.security.pojo.User;
import com.hkbu.security.repository.UserRepository;
import com.hkbu.security.vo.ResponseEnum;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null){
            throw new GlobalException(ResponseEnum.LOGIN_ERROR);
        }
        return new LoginUser(user, new ArrayList<>());
    }
}
