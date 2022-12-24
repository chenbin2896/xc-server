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

public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    private Log log = LogFactory.getLog(this.getClass());

    private final org.springframework.security.web.authentication.AuthenticationSuccessHandler delegate = new SavedRequestAwareAuthenticationSuccessHandler();

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

        UserJwt userJwt = (UserJwt) authentication.getPrincipal();

        String encode = AesUtil.encode(userJwt.getId());
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
