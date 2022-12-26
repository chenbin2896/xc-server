package com.xuecheng.search.controller;

import com.xuecheng.api.search.SearchControllerApi;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.search.service.EsCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
@RequestMapping("/search/course")
public class EsCourseController implements SearchControllerApi {
    @Autowired
    EsCourseService esCourseService;

    @Override
    @GetMapping(value = "/list/{page}/{size}")
    public ResponseResult list(@PathVariable("page") int page, @PathVariable("size") int size, CourseSearchParam courseSearchParam) {
        return ResponseResult.SUCCESS(esCourseService.list(page, size, courseSearchParam));
    }

    @Override
    @GetMapping("/getall/{id}")
    public ResponseResult getall(@PathVariable("id") String id) {
        return ResponseResult.SUCCESS(esCourseService.getall(id));
    }

    @Override
    @GetMapping(value = "/getmedia/{teachplanId}")
    public ResponseResult getmedia(@PathVariable("teachplanId") String teachplanId) {

        String[] teachplanIds = new String[]{teachplanId};
        QueryResult<TeachplanMediaPub> queryResult = esCourseService.getmedia(teachplanIds);
        return ResponseResult.SUCCESS(queryResult);
    }

    @Override
    @GetMapping("/getbase/{ids}")
    public ResponseResult getBase(@PathVariable("ids") String[] ids) {
        return ResponseResult.SUCCESS(esCourseService.getBase(ids));
    }
}
