package com.sixsixsix516.sunjwt.controller;

import com.sixsixsix516.sunjwt.annotation.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sun 2020/2/23 22:35
 */
@RestController
@RequestMapping("/test2")
public class Test2Controller {

    @Authentication
    @GetMapping("/test1")
    public String test1(){
        return "我加认证注解了，你不能直接访问到我";
    }

    @GetMapping("/test2")
    public String test2(){
        return "我没加认证注解，你可以直接访问到我";
    }
}
