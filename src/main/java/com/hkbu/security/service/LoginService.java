package com.hkbu.security.service;

import com.hkbu.security.pojo.User;
import com.hkbu.security.vo.ResponseBean;

public interface LoginService {

    ResponseBean login(User user);

    ResponseBean logout();
}
