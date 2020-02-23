package com.sixsixsix516.sunjwt.controller;

import com.sixsixsix516.sunjwt.annotation.Authentication;
import com.sixsixsix516.sunjwt.config.TokenConfig;
import com.sixsixsix516.sunjwt.constant.RedisKey;
import com.sixsixsix516.sunjwt.entity.User;
import com.sixsixsix516.sunjwt.service.UserService;
import com.sixsixsix516.sunjwt.utils.TokenUtil;
import com.sixsixsix516.sunjwt.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author sun 2020/2/23 20:09
 */
@RestController
public class UserController {


    /**
     * 用户登陆
     * 登陆的时候创建token
     */
    @PostMapping("/login")
    public Result login(User user) {
        User userBase = userService.findUser(user);
        if (userBase == null) {
            return Result.fail(-1, "登陆失败");
        }

        String token = TokenUtil.createToken(userBase.getPassword(), userBase.getId());
        // 将token存储在redis中，并设置失效时间
        stringRedisTemplate.opsForValue().set(String.format(RedisKey.LOGIN, userBase.getId()),
                token,
                TokenConfig.DEFAULT_FAILURE_TIME,
                TimeUnit.MILLISECONDS);
        return Result.success(0, "登陆成功", token);
    }


    /**
     * 获取当前用户信息的接口
     * 如果要获取当前用户信息，必须要先走认证才能获取到值,必须使用 @Authentication 注解在类或方法上修饰
     */
    @Authentication
    @GetMapping("/getUserInfo")
    public Result getUserInfo(HttpServletRequest request) {
        // 获取当前用户信息
        // 需要的信息可在 com.sixsixsix516.sunjwt.aop.AuthenticationAop.beforeValid 中最后设置
        String userId = (String) request.getAttribute("userId");

        return Result.success(0, "请求成功", userService.findUserById(userId));
    }

    /**
     * 退出登录
     */
    @Authentication
    @GetMapping("/logout")
    public Result logout(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        // 删除redis中的key
        stringRedisTemplate.delete(String.format(RedisKey.LOGIN, userId));
        return Result.success(0, "退出成功");

    }

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

}
