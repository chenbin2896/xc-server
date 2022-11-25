package com.xuecheng.framework.domain.filesystem.response;

import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

/**
 * Created by admin on 2018/3/5.
 */
@Data
@ToString
public class UploadFileResult extends ResponseResult {
    @Schema(name = "文件信息", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    FileSystem fileSystem;

    public UploadFileResult(ResultCode resultCode, FileSystem fileSystem) {
        super(resultCode);
        this.fileSystem = fileSystem;
    }

}
