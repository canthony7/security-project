package com.hkbu.security.service.impl;

import com.hkbu.security.exception.GlobalException;
import com.hkbu.security.pojo.LoginUser;
import com.hkbu.security.pojo.User;
import com.hkbu.security.repository.UserRepository;
import com.hkbu.security.service.MenuService;
import com.hkbu.security.service.RoleService;
import com.hkbu.security.vo.ResponseEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    UserRepository userRepository;

    @Resource
    RoleService roleService;

    @Resource
    MenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null){
            throw new GlobalException(ResponseEnum.LOGIN_ERROR);
        }
        // 获取权限列表
        List<Long> roleIds = roleService.findRoleIdByUserId(user.getId());
        List<String> permissions = menuService.findMenusByRoleIds(roleIds);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission.trim()));
        }
        return new LoginUser(user, authorities);
    }
}
