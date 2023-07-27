package com.hkbu.security.controller;

import com.hkbu.security.vo.ResponseBean;
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
    public ResponseBean test02(){
        return ResponseBean.success("funny day");
    }

}
