package com.hkbu.security.service.impl;

import com.hkbu.security.exception.GlobalException;
import com.hkbu.security.pojo.Role;
import com.hkbu.security.pojo.User;
import com.hkbu.security.repository.UserRepository;
import com.hkbu.security.service.RoleService;
import com.hkbu.security.vo.ResponseEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    UserRepository userRepository;

    @Override
    @Transactional
    public List<Long> findRoleIdByUserId(Long userId) {
        if (userId == null){
            throw new GlobalException(ResponseEnum.FAIL);
        }
        User user = new User();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){
            user = optionalUser.get();
        }
        List<Role> roles = user.getRoles();
        List<Long> roleIds = new ArrayList<>();
        if (roles.size() > 0){
            for (Role role : roles) {
                roleIds.add(role.getId());
            }
        }
        return roleIds;
    }
}
