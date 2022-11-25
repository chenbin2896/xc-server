package com.xuecheng.api.auth;

import com.xuecheng.framework.domain.ucenter.request.LoginRequest;
import com.xuecheng.framework.domain.ucenter.response.JwtResult;
import com.xuecheng.framework.domain.ucenter.response.LoginResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Created by Administrator.
 */
@Tag(name = "用户认证", description = "用户认证接口")
public interface AuthControllerApi {
    @Operation(summary = "登录")
    public LoginResult login(LoginRequest loginRequest);

    @Operation(summary = "退出")
    public ResponseResult logout();

    @Operation(summary = "查询用户jwt令牌")
    public JwtResult userjwt();
}
