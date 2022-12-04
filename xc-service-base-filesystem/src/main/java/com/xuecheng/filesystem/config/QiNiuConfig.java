package com.xuecheng.filesystem.config;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author chenbin.zhang
 * @date 2022/9/28
 */
@Component
public class QiNiuConfig {

    @Value("${qiniu.oss.accessKey}")
    private static  String accessKey;

    @Value("${qiniu.oss.secretKey}")
    private static  String secretKey;

    @Value("${qiniu.oss.bucket}")
    private static  String bucket;

    @Bean
    public UploadManager uploadManager (){
        Configuration cfg = new Configuration(Region.huadong());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        return new UploadManager(cfg);
    }

    public static String getToken () {

        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucket);
    }

}
