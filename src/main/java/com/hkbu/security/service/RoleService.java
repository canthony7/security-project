package com.hkbu.security.service;

import com.hkbu.security.pojo.Role;

import java.util.List;

public interface RoleService {

    List<Long> findRoleIdByUserId(Long userId);

}
