package com.hkbu.security.controller;

import com.hkbu.security.vo.ResponseBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/any")
    public ResponseBean test01(){
        return ResponseBean.success("any day");
    }

    @GetMapping("/funny")
    @PreAuthorize("hasAuthority('sys:book:delete')")
    public ResponseBean test02(){
        return ResponseBean.success("funny day");
    }

}
