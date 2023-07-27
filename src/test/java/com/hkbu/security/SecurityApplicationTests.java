package com.hkbu.security;

import com.hkbu.security.pojo.User;
import com.hkbu.security.repository.UserRepository;
import com.hkbu.security.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
class SecurityApplicationTests {

    @Resource
    UserRepository userRepository;

    @Resource
    RedisCache redisCache;

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

    }
}
