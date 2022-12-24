package com.xuecheng.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class MyLogoutSuccessHandler implements LogoutSuccessHandler {


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 20000);
        resultMap.put("msg", "success");
        writer.write(new ObjectMapper().writeValueAsString(resultMap));

        writer.flush();
        writer.close();

    }
}
