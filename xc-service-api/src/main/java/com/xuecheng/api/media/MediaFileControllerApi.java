package com.xuecheng.api.media;

import com.xuecheng.framework.domain.media.MediaFile;
import com.xuecheng.framework.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Created by Administrator.
 */
@Tag(name = "媒体文件管理", description = "媒体文件管理接口")
public interface MediaFileControllerApi {

    @Operation(summary = "我的媒资文件查询列表")
    public QueryResponseResult<MediaFile> findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest);

}

