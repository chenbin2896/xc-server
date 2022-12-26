package com.xuecheng.api.ucenter;

import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Created by Administrator.
 */
@Tag(name = "用户中心", description = "用户中心管理")
public interface UcenterControllerApi {
    @Operation(summary = "根据用户账号查询用户信息")
    public ResponseResult getUserext(String username);

    @Operation(summary = "根据用户ID查询用户信息")
    public ResponseResult getUserById(String userId);
}
