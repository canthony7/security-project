package com.hkbu.security;

import com.hkbu.security.pojo.Menu;
import com.hkbu.security.pojo.Role;
import com.hkbu.security.pojo.User;
import com.hkbu.security.repository.RoleRepository;
import com.hkbu.security.repository.UserRepository;
import com.hkbu.security.service.MenuService;
import com.hkbu.security.service.RoleService;
import com.hkbu.security.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SecurityApplicationTests {

    @Resource
    UserRepository userRepository;

    @Resource
    RedisCache redisCache;

    @Resource
    RoleRepository roleRepository;

    @Resource
    RoleService roleService;

    @Resource
    MenuService menuService;

    @Test
    void contextLoads() {
        User user = userRepository.findByUserName("zhangsan");
        System.out.println(user);
    }

    @Test
    void Test01(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        String result = encoder.encode("1234");
        System.out.println(result);
        boolean matches = encoder.matches("1234", result);
        System.out.println(matches);
    }

    @Test
    void Test02(){
        User user = userRepository.findByUserName("chet");
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            System.out.println(role.getRoleKey());
        }
    }

    @Test
    void Test03(){
        List<Long> list = roleService.findRoleIdByUserId(11L);
        System.out.println(list.size());
    }

    @Test
    @Transactional
    void Test04(){
        Role role = roleRepository.findByRoleKey("admin");
        List<Menu> menus = role.getMenus();
        System.out.println(menus.size());
    }

    @Test
    void Test05(){
        List<Long> roleIds = roleService.findRoleIdByUserId(11L);
        List<String> permission = menuService.findMenusByRoleIds(roleIds);
        for (String perm : permission) {
            System.out.println(perm);
        }

    }
}
