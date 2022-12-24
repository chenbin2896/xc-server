package com.xuecheng.framework.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;

@Slf4j
public class AesUtil {

    private static final AesBytesEncryptor aes = new AesBytesEncryptor("xc-user", "5c0744940b5c369b");

    public static String encode(String userId) {
        byte[] encrypt = aes.encrypt(userId.getBytes(StandardCharsets.UTF_8));
        return Base64Utils.encodeToString(encrypt);
    }

    public static String decode(String userId) {
        try {

            byte[] decrypt = aes.decrypt(Base64Utils.decodeFromString(userId));
            return new String(decrypt);
        }catch (Exception e) {
            log.error("解析token失败{}", e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        String encode = encode("123123");
        System.out.println(encode);
    }
}
