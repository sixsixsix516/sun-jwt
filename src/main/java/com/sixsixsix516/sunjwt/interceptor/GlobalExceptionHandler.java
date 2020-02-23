package com.sixsixsix516.sunjwt.interceptor;

import com.sixsixsix516.sunjwt.exception.AuthenticationException;
import com.sixsixsix516.sunjwt.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理类
 *
 * @author sun
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler
    public Result handlerAuthenticationException(AuthenticationException exception){
        // 发生异常后可以在这里处理些什么，比如发邮件等等
        return Result.fail(exception.getCode(),exception.getMessage());
    }
}
