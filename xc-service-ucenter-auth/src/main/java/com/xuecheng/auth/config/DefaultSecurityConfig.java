/*
 * Copyright 2020-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xuecheng.auth.config;

import com.xuecheng.auth.security.FederatedIdentityAuthenticationSuccessHandler;
import com.xuecheng.auth.security.FederatedIdentityConfigurer;
import com.xuecheng.auth.security.MyLogoutSuccessHandler;
import com.xuecheng.auth.security.UserRepositoryOAuth2UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Steve Riesenberg
 * @since 0.2.3
 */
@EnableWebSecurity
public class DefaultSecurityConfig {

    // @formatter:off
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        FederatedIdentityConfigurer federatedIdentityConfigurer = new FederatedIdentityConfigurer()
                .oauth2UserHandler(new UserRepositoryOAuth2UserHandler());
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .mvcMatchers("/assets/**", "/webjars/**", "/login", "/oauth2/consent","/test").permitAll()
                                .anyRequest().authenticated()
                )
                .cors(c -> corsConfigurationSource())
                .csrf()
                .disable()
                .formLogin(login->
                        login
                                .successHandler(new FederatedIdentityAuthenticationSuccessHandler()))
                .logout(logout->logout.logoutSuccessHandler(new MyLogoutSuccessHandler()))
                .apply(federatedIdentityConfigurer);
        return http.build();
    }
    // @formatter:on

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:9528","http://127.0.0.1:8080"));
        configuration.setAllowedOriginPatterns(Arrays.asList("http://127.0.0.1:[*]", "http://localhost:[*]", "http://www.test.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    // @formatter:off
//    @Bean
//    public UserDetailsService users() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("123456")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
    // @formatter:on

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
