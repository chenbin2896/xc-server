package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.SysDicthinaryControllerApi;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.SysDictionaryService;
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
@RequestMapping("/sys/dictionary")
public class SysDictionaryController implements SysDicthinaryControllerApi {

    @Autowired
    SysDictionaryService sysDictionaryService;

    @Override
    @GetMapping("/get/{type}")
    public ResponseResult getByType(@PathVariable("type") String type) {
        return ResponseResult.SUCCESS(sysDictionaryService.findByType(type));
    }
}
