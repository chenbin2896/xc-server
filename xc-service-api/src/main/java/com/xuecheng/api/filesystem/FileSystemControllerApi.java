package com.xuecheng.api.filesystem;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator.
 */
@Tag(name = "文件管理接口", description = "文件管理接口，提供文件的增、删、改、查")
public interface FileSystemControllerApi {

    //上传文件
    @Operation(summary = "上传文件接口")
    public UploadFileResult upload(MultipartFile multipartFile,
                                   String filetag,
                                   String businesskey,
                                   String metadata);

}
