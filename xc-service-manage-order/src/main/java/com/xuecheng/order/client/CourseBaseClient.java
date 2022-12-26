package com.xuecheng.order.client;

import com.xuecheng.framework.model.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator.
 */
@FeignClient(value = "XC-SERVICE-MANAGE-COURSE") //指定远程调用的服务名
@RequestMapping("/course")
public interface CourseBaseClient {
    //查询课程详情
    @GetMapping("/coursebase/{course_id}")
    public ResponseResult getCourseBaseById(@PathVariable("course_id") String courseId);


    @GetMapping(value = "/courseview/{id}", produces = {"application/json;charset=UTF-8"})
    public ResponseResult courseview(@PathVariable("id") String id);

}
