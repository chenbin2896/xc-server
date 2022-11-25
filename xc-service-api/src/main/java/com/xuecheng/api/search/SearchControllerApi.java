package com.xuecheng.api.search;

import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

/**
 * Created by Administrator.
 */

@Tag(name = "课程搜索", description = "课程搜索")
public interface SearchControllerApi {

    @Operation(summary = "课程搜索")
    public QueryResponseResult<CoursePub> list(int page, int size, CourseSearchParam courseSearchParam);

    @Operation(summary = "根据课程id查询课程信息")
    public Map<String, CoursePub> getall(String id);

    @Operation(summary = "获取课程基本信息")
    public Map<String, CoursePub> getBase(String[] ids);

    @Operation(summary = "根据课程计划id查询课程媒资信息")
    public TeachplanMediaPub getmedia(String id);
}
