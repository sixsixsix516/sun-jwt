package com.sixsixsix516.sunjwt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

/**
 * token 工具类
 *
 * @author sun 2020/2/23 21:56
 */
public class TokenUtil {

    private TokenUtil() {
    }

    /**
     * 返回生成的token
     *
     * @param sign 签名
     * @param data token需要携带的数据
     */
    public static String createToken(String sign, String... data) {
        // 将 user id 保存到 token 里面
        return JWT.create().
                withAudience(data)
                // token的密钥
                .sign(Algorithm.HMAC256(sign));
    }


    /**
     * 校验token是否合法
     * @return true代表成功
     */
    public static boolean verify(String token, String sign) {
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(sign)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }
}
