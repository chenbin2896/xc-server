package com.xuecheng.api.media;

import com.xuecheng.framework.domain.media.response.CheckChunkResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator.
 */
@Tag(name = "媒资管理接口", description = "媒资管理接口，提供文件上传、处理等接口")
public interface MediaUploadControllerApi {

    //文件上传前的准备工作,校验文件是否存在
    @Operation(summary = "文件上传注册")
    public ResponseResult register(String fileMd5,
                                   String fileName,
                                   Long fileSize,
                                   String mimetype,
                                   String fileExt);

    @Operation(summary = "校验分块文件是否存在")
    public CheckChunkResult checkchunk(String fileMd5,
                                       Integer chunk,
                                       Integer chunkSize);

    @Operation(summary = "上传分块")
    public ResponseResult uploadchunk(MultipartFile file,
                                      String fileMd5,
                                      Integer chunk);

    @Operation(summary = "合并分块")
    public ResponseResult mergechunks(String fileMd5,
                                      String fileName,
                                      Long fileSize,
                                      String mimetype,
                                      String fileExt);

}
