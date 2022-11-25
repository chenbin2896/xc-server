package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CategoryControllerApi;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_course.client.CmsPageClient;
import com.xuecheng.manage_course.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
@RequestMapping("/category")
public class CategoryController implements CategoryControllerApi {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CmsPageClient cmaPageClient;

    @Override
    @GetMapping("/list")
    public QueryResponseResult<CategoryNode> findList() {
        return categoryService.findCategoryList();
    }
}
