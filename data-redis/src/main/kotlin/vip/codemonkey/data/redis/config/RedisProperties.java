package vip.codemonkey.data.redis.config;

/**
 * @Author：xingyc
 * @Description：Redis 基本环境信息
 * @CreateDate：13:48 2018/9/17
 */

public class RedisProperties {

    private String nodes;

    private Integer commandTimeout;

    private Integer maxAttempts;

    private Integer maxRedirects;

    private Integer maxActive;

    private Integer maxWait;

    private Integer maxIdle;

    private Integer minIdle;

    private boolean testOnBorrow;

}