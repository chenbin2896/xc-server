package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 17:24
 **/
@RestController
@RequestMapping("/page")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    private PageService pageService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @GetMapping("/list/{page}/{size}")
    public ResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        QueryResult<CmsPage> list = pageService.findList(page, size, queryPageRequest);
        //调用service
        return ResponseResult.SUCCESS(list);
    }

    @Override
    @PostMapping("/add")
    public ResponseResult add(@RequestBody CmsPage cmsPage) {
        return ResponseResult.SUCCESS(pageService.add(cmsPage));
    }

    @Override
    @GetMapping("/get/{id}")
    public ResponseResult findById(@PathVariable("id") String id) {

        CmsPage cmsPage = pageService.getById(id);
        return ResponseResult.SUCCESS(cmsPage);
    }

    @Override
    @PutMapping("/edit/{id}")
    public ResponseResult edit(@PathVariable("id") String id, @RequestBody CmsPage cmsPage) {
        pageService.update(id, cmsPage);
        return ResponseResult.SUCCESS();
    }


    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable("id") String id) {
        pageService.delete(id);
        return ResponseResult.SUCCESS();
    }

    @Override
    @PostMapping("/postPage/{pageId}")
    public ResponseResult post(@PathVariable("pageId") String pageId) {
        pageService.post(pageId);
        return ResponseResult.SUCCESS();
    }

    @Override
    @PostMapping("/save")
    public ResponseResult save(@RequestBody CmsPage cmsPage) {
        return ResponseResult.SUCCESS(pageService.save(cmsPage));
    }

    @Override
    @PostMapping("/postPageQuick")
    public ResponseResult postPageQuick(@RequestBody CmsPage cmsPage) {
        return ResponseResult.SUCCESS(pageService.postPageQuick(cmsPage));
    }
}
