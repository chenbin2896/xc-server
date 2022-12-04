package com.xuecheng.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.xuecheng.filesystem.config.QiNiuConfig;
import com.xuecheng.filesystem.dao.FileSystemRepository;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class FileSystemService {


    @Autowired
    FileSystemRepository fileSystemRepository;

    @Autowired
    private UploadManager uploadManager;

    //上传文件
    public UploadFileResult upload(MultipartFile multipartFile,
                                          String filetag,
                                          String businesskey,
                                          String metadata) throws IOException {
        if (multipartFile == null) {
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
        //第一步：将文件上传到fastDFS中，得到一个文件id
        byte[] bytes = multipartFile.getBytes();
        String fileId = uploadByQiNiu(bytes);

        if (StringUtils.isEmpty(fileId)) {
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        }
        //第二步：将文件id及其它文件信息存储到mongodb中。
        FileSystem fileSystem = new FileSystem();
        fileSystem.setFileId(fileId);
        fileSystem.setFilePath(fileId);
        fileSystem.setFiletag(filetag);
        fileSystem.setBusinesskey(businesskey);
        fileSystem.setFileName(multipartFile.getOriginalFilename());
        fileSystem.setFileType(multipartFile.getContentType());
        if (StringUtils.isNotEmpty(metadata)) {
            try {
                Map map = JSON.parseObject(metadata, Map.class);
                fileSystem.setMetadata(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fileSystemRepository.save(fileSystem);
        return new UploadFileResult(CommonCode.SUCCESS, fileSystem);
    }

    private String uploadByQiNiu(byte[] bytes) throws QiniuException {

        Response response = uploadManager.put(bytes, null, QiNiuConfig.getToken());
        String info = response.bodyString();
        JSONObject jsonObject = JSON.parseObject(info);

        return jsonObject.getString("key");

    }
}
