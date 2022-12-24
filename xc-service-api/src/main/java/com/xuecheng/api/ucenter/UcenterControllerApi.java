package com.xuecheng.api.ucenter;

import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Created by Administrator.
 */
@Tag(name = "用户中心", description = "用户中心管理")
public interface UcenterControllerApi {
    @Operation(summary = "根据用户账号查询用户信息")
    public XcUserExt getUserext(String userId, String username);

    @Operation(summary = "根据用户ID查询用户信息")
    public XcUserExt getUserById(String userId);
}
