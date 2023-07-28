package com.hkbu.security.service.impl;

import com.hkbu.security.pojo.Menu;
import com.hkbu.security.pojo.Role;
import com.hkbu.security.repository.RoleRepository;
import com.hkbu.security.service.MenuService;
import com.hkbu.security.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    RoleRepository roleRepository;

    @Override
    public List<String> findMenusByRoleIds(List<Long> roleIds) {
        List<Role> roles = roleRepository.findAllById(roleIds);
        List<String> permission = new ArrayList<>();
        // 每个角色的菜单
        List<Menu> menus;
        if (roles.size() > 0){
            for (Role role : roles) {
                // 得到了某个角色菜单，添加到permission
                menus = role.getMenus();
                if (menus.size() > 0){
                    for (Menu menu : menus) {
                        if (!permission.contains(menu.getPermKey())){
                            permission.add(menu.getPermKey());
                        }
                    }
                }
            }
        }
        return permission;
    }
}
