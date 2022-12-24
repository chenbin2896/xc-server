package com.xuecheng.ucenter.config;

import com.xuecheng.ucenter.handler.ParamsInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Bean
    public ParamsInterceptor paramsInterceptor () {
        return new ParamsInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(paramsInterceptor())
                .addPathPatterns("/**");

    }
}
