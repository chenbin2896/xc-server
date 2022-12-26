package com.xuecheng.api.learning;

import com.xuecheng.framework.domain.learning.request.LearningCourseRequest;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Created by Administrator.
 */
@Tag(name = "录播课程学习管理", description = "录播课程学习管理")
public interface CourseLearningControllerApi {

    @Operation(summary = "获取课程学习地址")
    public ResponseResult getmedia(String courseId, String teachplanId);

    @Operation(summary = "获取课程列表")
    public ResponseResult getCourseList(int page, int size, String userId, LearningCourseRequest learningCourseRequest);


    @Operation(summary = "报名课程")
    public ResponseResult addopencourse(String userId, String courseId);

}
