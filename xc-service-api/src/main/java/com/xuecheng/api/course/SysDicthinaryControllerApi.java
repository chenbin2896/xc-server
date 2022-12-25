package com.xuecheng.api.course;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Created by Administrator.
 */

@Tag(name = "数据字典接口", description = "提供数据字典接口的管理、查询功能")
public interface SysDicthinaryControllerApi {

    @Operation(summary = "数据字典查询接口")
    public ResponseResult getByType(String type);

}
