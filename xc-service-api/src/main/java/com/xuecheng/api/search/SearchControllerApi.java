package com.xuecheng.api.search;

import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Created by Administrator.
 */

@Tag(name = "课程搜索", description = "课程搜索")
public interface SearchControllerApi {

    @Operation(summary = "课程搜索")
    public ResponseResult list(int page, int size, CourseSearchParam courseSearchParam);

    @Operation(summary = "根据课程id查询课程信息")
    public ResponseResult getall(String id);

    @Operation(summary = "获取课程基本信息")
    public ResponseResult getBase(String[] ids);

    @Operation(summary = "根据课程计划id查询课程媒资信息")
    public ResponseResult getmedia(String id);
}
