package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "cms页面管理接口", description = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {
    //页面查询
    @Operation(summary = "分页查询页面列表")
    @Parameters({
            @Parameter(name = "page", description = "页码", required = true),
            @Parameter(name = "size", description = "每页记录数", required = true)
    })
    ResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    @Operation(summary = "添加页面")
    ResponseResult add(CmsPage cmsPage);

    @Operation(summary = "通过ID查询页面")
    ResponseResult findById(String id);

    @Operation(summary = "修改页面")
    ResponseResult edit(String id, CmsPage cmsPage);

    @Operation(summary = "删除页面")
    ResponseResult delete(String id);


    @Operation(summary = "发布页面")
    ResponseResult post(String pageId);

    @Operation(summary = "保存页面")
    public ResponseResult save(CmsPage cmsPage);

    @Operation(summary = "一键发布页面")
    public ResponseResult postPageQuick(CmsPage cmsPage);

}
