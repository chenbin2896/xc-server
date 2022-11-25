package com.xuecheng.govern.gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class AuthService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //查询令牌的有效期
    public Long getExpire(String access_token) {
        //key
        String key = "user_token:" + access_token;
        return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
}
