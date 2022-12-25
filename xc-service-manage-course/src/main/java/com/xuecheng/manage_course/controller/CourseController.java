package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_course.client.CmsPageClient;
import com.xuecheng.manage_course.client.UserServiceClient;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
public class CourseController extends BaseController implements CourseControllerApi {

    @Autowired
    CourseService courseService;

    @Autowired
    CmsPageClient cmaPageClient;

    @Autowired
    UserServiceClient userServiceClient;

    /**
     * 添加课程
     *
     * @param courseBase
     * @return
     */
    @Override
    @PostMapping("/coursebase/add")
    public ResponseResult addCourseBase(@RequestAttribute String userId, @RequestBody CourseBase courseBase) {
        XcUserExt user = userServiceClient.getUserById(userId);

        courseBase.setCompanyId(user.getCompanyId());
        courseBase.setUserId(userId);

        return courseService.add(courseBase);
    }

    /**
     * 获取课程
     *
     * @param courseId
     * @return
     */
    @Override
    @GetMapping("/coursebase/{course_id}")
    public ResponseResult getCourseBaseById(@PathVariable("course_id") String courseId) {
        return ResponseResult.SUCCESS(courseService.getCourseBase(courseId));
    }

    /**
     * 编辑课程
     *
     * @param courseId
     * @param courseBase
     * @return
     */
    @Override
    @PostMapping("/coursebase/update/{course_id}")
    public ResponseResult updateCourseBase(@PathVariable("course_id") String courseId, @RequestBody CourseBase courseBase) {
        return courseService.updateCourse(courseId, courseBase);
    }

    /**
     * 获取课程营销信息
     *
     * @param courseMarketId
     * @return
     */
    @Override
    @GetMapping("/coursemarket/{course_id}")
    public ResponseResult getCourseMarketById(@PathVariable("course_id") String courseMarketId) {
        return ResponseResult.SUCCESS(courseService.getCourseMarketById(courseMarketId));
    }

    /**
     * 更新课程营销信息
     *
     * @param courseMarketId
     * @param courseMarket
     * @return
     */
    @Override
    @PostMapping("/coursemarket/update/{coursemarket_id}")
    public ResponseResult updateCourseMarket(@PathVariable("coursemarket_id") String courseMarketId, @RequestBody CourseMarket courseMarket) {
        return courseService.updateCourseMarket(courseMarketId, courseMarket);
    }

    //当用户拥有course_teachplan_list权限时候方可访问此方法
    //@PreAuthorize("hasAuthority('course_teachplan_list')")
    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public ResponseResult findTeachplanList(@PathVariable("courseId") String courseId) {
        TeachplanNode teachplanList = courseService.findTeachplanList(courseId);
        return ResponseResult.SUCCESS(teachplanList);

    }

    //@PreAuthorize("hasAuthority('course_teachplan_add')")
    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {

        return courseService.addTeachplan(teachplan);
    }

    @Override
    @PostMapping("/coursepic/add")
    public ResponseResult addCoursePic(@RequestParam("courseId") String courseId, @RequestParam("pic") String pic) {
        return courseService.addCoursePic(courseId, pic);
    }

    //当用户拥有course_pic_list权限时候方可访问此方法
    //@PreAuthorize("hasAuthority('course_pic_list')")
    @Override
    @GetMapping("/coursepic/list/{courseId}")
    public ResponseResult findCoursePic(@PathVariable("courseId") String courseId) {
        return ResponseResult.SUCCESS(courseService.findCoursePic(courseId));
    }

    @Override
    @DeleteMapping("/coursepic/delete")
    public ResponseResult deleteCoursePic(@RequestParam("courseId") String courseId) {
        return courseService.deleteCoursePic(courseId);
    }

    @Override
    @GetMapping(value = "/courseview/{id}", produces = {"application/json;charset=UTF-8"})
    public ResponseResult courseview(@PathVariable("id") String id) {
        return ResponseResult.SUCCESS(courseService.getCoruseView(id));
    }

    @Override
    @PostMapping("/preview/{id}")
    public ResponseResult preview(@PathVariable("id") String id) {
        return courseService.preview(id);
    }

    @Override
    @PostMapping("/publish/{id}")
    public ResponseResult publish(@PathVariable("id") String id) {
        return ResponseResult.SUCCESS(courseService.publish(id));
    }

    @Override
    @PostMapping("/savemedia")
    public ResponseResult savemedia(@RequestBody TeachplanMedia teachplanMedia) {
        return ResponseResult.SUCCESS(courseService.savemedia(teachplanMedia));
    }

    @Override
    @GetMapping("/courseview/list/{ids}")
    public ResponseResult getCourseBaseList(@PathVariable("ids") String[] ids) {
        Map<String, CourseBase> map = new HashMap<>();
        for (String id : ids) {
            CourseBase courseBaseById = courseService.findCourseBaseById(id);
            map.put(id, courseBaseById);
        }
        return ResponseResult.SUCCESS(map);
    }

    @Override
    @PostMapping("/courseinfo/list/{page}/{size}")
    public QueryResponseResult<CourseInfo> findCourseList(@RequestAttribute String userId,
                                                          @PathVariable("page") int page,
                                                          @PathVariable("size") int size,
                                                          @RequestBody CourseListRequest courseListRequest) {

        XcUserExt user = userServiceClient.getUserById(userId);


        return courseService.findCourseList(user.getCompanyId(), userId, page, size, courseListRequest);
    }
}
