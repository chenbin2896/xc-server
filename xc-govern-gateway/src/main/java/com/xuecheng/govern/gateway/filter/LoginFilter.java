package com.xuecheng.govern.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.utils.AesUtil;
import com.xuecheng.govern.gateway.common.WhiteList;
import com.xuecheng.govern.gateway.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
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

    @Autowired
    WhiteList whiteList;

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

        RequestPath path = request.getPath();
        System.out.println(path.contextPath());

        for (String url : whiteList.getInclude()) {
            PathMatcher matcher = new AntPathMatcher();

            boolean match = matcher.match(url, path.value());
            if (match) {
                return chain.filter(exchange);
            }
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

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {

        return 0;
    }

    public static void main(String[] args) {


        String encode = AesUtil.encode("49");

        System.out.println(encode);
    }
}