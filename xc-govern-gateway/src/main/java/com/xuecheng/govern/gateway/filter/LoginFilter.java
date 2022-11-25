package com.xuecheng.govern.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.xuecheng.govern.gateway.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份校验过虑器
 *
 * @author Administrator
 * @version 1.0
 **/

@Configuration
@Slf4j
public class LoginFilter implements GlobalFilter, Ordered {

    @Autowired
    AuthService authService;

    //拒绝访问
    private Mono<Void> accessDenied(ServerWebExchange exchange) {
        //得到response
        ServerHttpResponse response = exchange.getResponse();

        Map<String, String> map = new HashMap<>();
        map.put("msg", "请登录");

        byte[] bytes = JSON.toJSONString(map).getBytes(StandardCharsets.UTF_8);

        DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

        return response.writeWith(Mono.just(dataBuffer));
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("global filter.....");

        //得到request
        ServerHttpRequest request = exchange.getRequest();

        //取cookie中的身份令牌
        MultiValueMap<String, HttpCookie> cookieMultiValueMap = request.getCookies();
        HttpCookie cookie = cookieMultiValueMap.getFirst("uid");

        if (cookie == null || StringUtils.isEmpty(cookie.getValue())) {
            //拒绝访问
            return accessDenied(exchange);
        }
        //从header中取jwt
        HttpHeaders headers = request.getHeaders();
        String authorization = headers.getFirst("Authorization");

        if (StringUtils.isEmpty(authorization)) {
            return accessDenied(exchange);
        }
        if (!authorization.startsWith("Bearer ")) {
            return accessDenied(exchange);
        }

        //从redis取出jwt的过期时间
        Long expire = authService.getExpire(cookie.getValue());
        if (expire == null || expire < 0) {
            //拒绝访问
            return accessDenied(exchange);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {

        return 0;
    }


}