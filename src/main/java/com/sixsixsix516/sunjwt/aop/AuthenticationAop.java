package com.sixsixsix516.sunjwt.aop;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.sixsixsix516.sunjwt.constant.RedisKey;
import com.sixsixsix516.sunjwt.entity.User;
import com.sixsixsix516.sunjwt.exception.AuthenticationException;
import com.sixsixsix516.sunjwt.service.UserService;
import com.sixsixsix516.sunjwt.utils.TokenUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author sun 2020/2/23 21:12
 */
@Aspect
@Component
@RequestMapping
public class AuthenticationAop {

    /**
     * 拦截类上或方法上有{@Authentication}注解的方法
     */
    @Pointcut("@annotation(com.sixsixsix516.sunjwt.annotation.Authentication) || @within(com.sixsixsix516.sunjwt.annotation.Authentication)")
    public void pointcut() {
    }


    @Before("pointcut()")
    public void beforeValid() {
        // 1.获取token
        String token = getToken();

        // 2.校验token
        if (StringUtils.isEmpty(token)) {
            fail();
        }

        // 3.取出token里携带的数据
        List<String> audience = null;
        try {
            audience = JWT.decode(token).getAudience();
        } catch (JWTDecodeException j) {
            fail();
        }

        // 4. 验证token是否合法
        String userId = audience.get(0);

        String userToken = stringRedisTemplate.opsForValue().get(String.format(RedisKey.LOGIN, userId));
        // 如果是空代表已经失效了 ， 如果不相等代表写错等问题
        if(StringUtils.isEmpty(userToken) || ! userToken.equals(token)){
            fail();
        }

        User user = userService.findUserById(userId);
        if (user == null || !TokenUtil.verify(token, user.getPassword())) {
            fail();
        }

        // 将后面程中需要的数据存储起来
        // 在 com.sixsixsix516.sunjwt.controller.UserController.getUserInfo 可见用法
        request.setAttribute("userId",userId);
    }

    /**
     * 验证失败，抛出异常
     */
    private void fail() {
        throw new AuthenticationException(401, "您还未登陆！");
    }

    /**
     * 从请求中获取token
     */
    private String getToken() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 还可以在请求中放别得东西，如果需要的话
        return request.getParameter("token");
    }

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private HttpServletRequest request;
}
