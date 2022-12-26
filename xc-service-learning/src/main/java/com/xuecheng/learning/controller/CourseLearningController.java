package com.xuecheng.learning.controller;

import com.xuecheng.api.learning.CourseLearningControllerApi;
import com.xuecheng.framework.domain.learning.XcLearningCourse;
import com.xuecheng.framework.domain.learning.request.LearningCourseRequest;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.learning.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
public class CourseLearningController extends BaseController implements CourseLearningControllerApi {

    @Autowired
    private LearningService learningService;

    @Override
    @GetMapping("/course/getmedia/{courseId}/{teachplanId}")
    public ResponseResult getmedia(@PathVariable("courseId") String courseId,
                                   @PathVariable("teachplanId") String teachplanId) {
        return learningService.getmedia(courseId, teachplanId);
    }

    @Override
    @PostMapping("/course/choosecourse/list/{page}/{size}")
    public ResponseResult getCourseList(@PathVariable("page") int page,
                                        @PathVariable("size") int size,
                                        @RequestAttribute String userId,
                                        @RequestBody LearningCourseRequest learningCourseRequest) {
        return learningService.findXcCourseList(page, size, userId, learningCourseRequest);
    }

    @Override
    @PostMapping("/choosecourse/addopencourse/{courseId}")
    public ResponseResult addopencourse(@RequestAttribute String userId, @PathVariable("courseId") String courseId) {

        XcLearningCourse xcLearningCourse = new XcLearningCourse();
        xcLearningCourse.setCourseId(courseId);
        xcLearningCourse.setUserId(userId);
        xcLearningCourse.setStartTime(new Date());
        xcLearningCourse.setValid("0");
        xcLearningCourse.setStatus("501001");
        return learningService.addOpenCourse(xcLearningCourse);
    }
}
