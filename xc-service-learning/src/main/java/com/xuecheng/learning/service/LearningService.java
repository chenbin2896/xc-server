package com.xuecheng.learning.service;

import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.learning.XcLearningCourse;
import com.xuecheng.framework.domain.learning.request.LearningCourseRequest;
import com.xuecheng.framework.domain.learning.respones.LearningCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.learning.client.CourseSearchClient;
import com.xuecheng.learning.dao.LearningCourseJPA;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class LearningService {

    @Autowired
    private CourseSearchClient courseSearchClient;

    @Autowired
    private LearningCourseJPA learningCourseJPA;

    //获取课程学习地址（视频播放地址）
    public ResponseResult getmedia(String courseId, String teachplanId) {
        //校验学生的学生权限...

        //远程调用搜索服务查询课程计划所对应的课程媒资信息
        TeachplanMediaPub teachplanMediaPub = courseSearchClient.getmedia(teachplanId);
        if (teachplanMediaPub == null || StringUtils.isEmpty(teachplanMediaPub.getMediaUrl())) {
            ExceptionCast.cast(LearningCode.LEARNING_GETMEDIA_ERROR);
        }
        return ResponseResult.SUCCESS(teachplanMediaPub.getMediaUrl());
    }

    public ResponseResult findXcCourseList(int page, int size, String userId, LearningCourseRequest learningCourseRequest) {
        XcLearningCourse xcLearningCourse = new XcLearningCourse();
        xcLearningCourse.setUserId(userId);

        Example<XcLearningCourse> example = Example.of(xcLearningCourse);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<XcLearningCourse> all = learningCourseJPA.findAll(example, pageable);

        //总记录数
        long total = all.getTotalElements();
        //数据列表
        List<XcLearningCourse> content = all.getContent();
        //返回的数据集
        QueryResult<XcLearningCourse> queryResult = new QueryResult<>();
        queryResult.setList(content);
        queryResult.setTotal(total);

        //返回结果
        return ResponseResult.SUCCESS(queryResult);
    }

    public ResponseResult addOpenCourse(XcLearningCourse xcLearningCourse) {
        XcLearningCourse xcLearningCourse1 = new XcLearningCourse();
        xcLearningCourse1.setCourseId(xcLearningCourse.getCourseId());
        xcLearningCourse1.setUserId(xcLearningCourse1.getId());
        Example<XcLearningCourse> example = Example.of(xcLearningCourse1);
        Optional<XcLearningCourse> one = learningCourseJPA.findOne(example);
        if (one.isPresent()) {
            return ResponseResult.FAIL("您已报名该课程");
        }

        learningCourseJPA.save(xcLearningCourse);
        return new ResponseResult(CommonCode.SUCCESS);
    }

}
