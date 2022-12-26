package com.xuecheng.api.course;

import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Created by Administrator.
 */

@Tag(name = "课程分类管理", description = "课程分类管理")
public interface CategoryControllerApi {

    @Operation(summary = "查询分类")
    public ResponseResult findList();
}
