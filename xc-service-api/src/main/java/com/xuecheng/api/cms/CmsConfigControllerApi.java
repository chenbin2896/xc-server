package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author User
 * @date 2019/11/22
 * @description
 */
@Tag(name = "cms配置管理接口", description = "cms配置管理接口，提供数据模型的增、删、改、查")
public interface CmsConfigControllerApi {

    @Operation(summary = "根据id查询cms配置信息")
    CmsConfig getModel(String id);
}
