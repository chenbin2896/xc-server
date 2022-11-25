package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.*;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

/**
 * Created by Administrator.
 */

@Tag(name = "课程管理接口", description = "课程管理接口，提供课程的增、删、改、查")
public interface CourseControllerApi {
    @Operation(summary = "课程计划查询")
    public TeachplanNode findTeachplanList(String courseId);

    @Operation(summary = "添加课程计划")
    public ResponseResult addTeachplan(Teachplan teachplan);

    @Operation(summary = "查询我的课程列表")
    public QueryResponseResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest);

    @Operation(summary = "添加课程")
    public ResponseResult addCourseBase(CourseBase courseBase);

    @Operation(summary = "获取课程基础信息")
    public CourseBase getCourseBaseById(String courseId);

    @Operation(summary = "编辑课程基础信息")
    public ResponseResult updateCourseBase(String courseId, CourseBase courseBase);

    @Operation(summary = "获取课程营销信息")
    public CourseMarket getCourseMarketById(String courseMarketId);

    @Operation(summary = "编辑课程营销信息")
    public ResponseResult updateCourseMarket(String courseMarketId, CourseMarket courseMarket);

    @Operation(summary = "课程视图查询")
    public CourseView courseview(String id);

    @Operation(summary = "获取多个课程信息")
    public Map<String, CourseBase> getCourseBaseList(String[] ids);

    @Operation(summary = "课程预览")
    public CoursePublishResult preview(String id);

    @Operation(summary = "添加课程图片")
    public ResponseResult addCoursePic(String courseId, String pic);

    @Operation(summary = "查询课程图片")
    public CoursePic findCoursePic(String courseId);

    @Operation(summary = "删除课程图片")
    public ResponseResult deleteCoursePic(String courseId);

    @Operation(summary = "课程发布")
    public CoursePublishResult publish(String id);

    @Operation(summary = "保存课程计划与媒资文件关联")
    public ResponseResult savemedia(TeachplanMedia teachplanMedia);
}
