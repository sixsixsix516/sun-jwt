package com.sixsixsix516.sunjwt.config;

/**
 * token的配置类
 *
 * @author sun 2020/2/23 21:44
 */
public interface TokenConfig {

    /**
     * token失效时间
     * 单位 毫秒
     *
     * 默认失效时间(7天) （60*60*24*7） * 1000 = 604800000
     */
    long DEFAULT_FAILURE_TIME = 604800_000;
}
