package com.sixsixsix516.sunjwt.controller;

import com.sixsixsix516.sunjwt.annotation.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制类
 * 此类被 @Authentication 修饰，所有方法需要认证
 * @author sun 2020/2/23 22:33
 */
@Authentication
@RestController
@RequestMapping("/test1")
public class TestController {

    @GetMapping("/test1")
    public String test1(){
        return "我在类上加认证注解了，你不能直接访问到我-- test1";
    }

    @GetMapping("/test2")
    public String test2(){
        return "我在类上加认证注解了，你不能直接访问到我 -- test2";
    }

    @GetMapping("/test3")
    public String test3(){
        return "我在类上加认证注解了，你不能直接访问到我 -- test3";
    }
}
