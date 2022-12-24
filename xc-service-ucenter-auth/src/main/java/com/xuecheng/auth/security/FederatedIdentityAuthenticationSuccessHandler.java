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
package com.xuecheng.auth.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuecheng.auth.service.UserJwt;
import com.xuecheng.framework.utils.AesUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An {@link AuthenticationSuccessHandler} for capturing the {@link OidcUser} or
 * {@link OAuth2User} for Federated Account Linking or JIT Account Provisioning.
 *
 * @author Steve Riesenberg
 * @since 0.2.3
 */
public class FederatedIdentityAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private Log log = LogFactory.getLog(this.getClass());

    private final AuthenticationSuccessHandler delegate = new SavedRequestAwareAuthenticationSuccessHandler();

    private Consumer<OAuth2User> oauth2UserHandler = (user) -> {
    };

    private Consumer<OidcUser> oidcUserHandler = (user) -> this.oauth2UserHandler.accept(user);


    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            if (authentication.getPrincipal() instanceof OidcUser) {

                this.oidcUserHandler.accept((OidcUser) authentication.getPrincipal());
            } else if (authentication.getPrincipal() instanceof OAuth2User) {
                this.oauth2UserHandler.accept((OAuth2User) authentication.getPrincipal());
            }
        }

        // 返回json

        // 前端根据路径中的redirectUrl跳转

        String redirectUrl = null;
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        if (savedRequest != null) {
            this.clearAuthenticationAttributes(request);
            redirectUrl = savedRequest.getRedirectUrl();
        }

        log.debug("redirectUrl: " + redirectUrl);

        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> data = new HashMap<>();


        UserJwt principal = (UserJwt) authentication.getPrincipal();

        System.out.println(principal);

        String encode = AesUtil.encode(principal.getId());

        // 返回jwt
        data.put("token", encode);

        map.put("code", 20000);
        map.put("msg", "登录成功");
        map.put("data", data);

        if (StringUtils.hasText(redirectUrl)) {
            data.put("redirectUrl", redirectUrl);
        }
        writer.write(new ObjectMapper().writeValueAsString(map));

        writer.flush();
        writer.close();

//		this.delegate.onAuthenticationSuccess(request, response, authentication);
    }

    public void setOAuth2UserHandler(Consumer<OAuth2User> oauth2UserHandler) {
        this.oauth2UserHandler = oauth2UserHandler;
    }

    public void setOidcUserHandler(Consumer<OidcUser> oidcUserHandler) {
        this.oidcUserHandler = oidcUserHandler;
    }


    public static void main(String[] args) {
//        Map<String, Object> claim = new HashMap<>();
//
//        claim.put("uid", "123");
//
//        String token = Jwts.builder()
//                .setClaims(claim)
//                .signWith(SignatureAlgorithm.HS512, "sdfasdf").compact();
//
//        System.out.println(token);
//
//        Claims claims = Jwts.parser().setSigningKey("sdfasdf").parseClaimsJws(token).getBody();
//        String uid = (String) claims.get("uid");
//
//        System.out.println(uid);
//
//
//        String compact = Jwts.builder().setClaims(claim).compact();
//
//        Claims sdfasdf = Jwts.parser().setSigningKey("sdfasdf").parseClaimsJwt(compact).getBody();
//
//        System.out.println(sdfasdf.get("uid"));


        AesBytesEncryptor aes = new AesBytesEncryptor("abc", "5c0744940b5c369b");

        byte[] encrypt = aes.encrypt("1234567890098765".getBytes(StandardCharsets.UTF_8));

        String s1 = Base64Utils.encodeToString(encrypt);
        System.out.println(s1);



        byte[] decrypt = aes.decrypt(Base64Utils.decodeFromString(s1));

        String s = new String(decrypt);
        System.out.println(s);


    }


}
