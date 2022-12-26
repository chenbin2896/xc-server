package com.xuecheng.manage_cms_client.handler;

import com.xuecheng.framework.utils.AesUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ParamsInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            String token = authorization.substring(7);
            String userId = AesUtil.decode(token);
            request.setAttribute("userId", userId);
        }

        return true;
    }
}
