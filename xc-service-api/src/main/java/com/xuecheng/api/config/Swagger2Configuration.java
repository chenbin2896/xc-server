package com.xuecheng.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//
@Configuration
public class Swagger2Configuration {
    @Bean
    public OpenAPI mallTinyOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("xuecheng.com ")
                        .description("SpringDoc API 演示")
                        .version("v1.0.0")
                        .description("学成在线"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("xc")
                .pathsToMatch("/learning/**", "/cms/**", "/**")
                .build();
    }


}
