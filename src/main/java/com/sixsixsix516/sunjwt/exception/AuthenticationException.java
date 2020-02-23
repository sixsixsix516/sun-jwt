package com.sixsixsix516.sunjwt.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 认证失败异常类
 *
 * @author sun 2020/2/23 21:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationException extends RuntimeException {

    private int code;
    private String message;

}
