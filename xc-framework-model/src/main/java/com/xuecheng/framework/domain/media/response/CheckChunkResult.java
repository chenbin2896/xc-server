package com.xuecheng.framework.domain.media.response;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by admin on 2018/3/5.
 */
@Data
@ToString
@NoArgsConstructor
public class CheckChunkResult extends ResponseResult {

    @Schema(name = "文件分块存在标记", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    boolean fileExist;

    public CheckChunkResult(ResultCode resultCode, boolean fileExist) {
        super(resultCode);
        this.fileExist = fileExist;
    }
}
