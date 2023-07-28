package com.hkbu.security.service;

import java.util.List;

public interface MenuService {

    List<String> findMenusByRoleIds(List<Long> roleIds);

}
