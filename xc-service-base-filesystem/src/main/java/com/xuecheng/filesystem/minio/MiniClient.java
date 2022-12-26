package com.xuecheng.filesystem.minio;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.regex.Pattern;

public class MiniClient {
    private static final MinioClient minioClient;

    private static final String bucket = "test";

    private static final DateTimeFormatter KEY_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final Pattern INVALID_FILE_NAME_REG = Pattern.compile("[<>:\"/\\\\|?*\\u0000-\\u001F]");

    static {
        minioClient = MinioClient.builder()
                .endpoint("http://docker-base:9000")
                .credentials("ub8U4W3KyCDVz4vB", "vXi4v5CRe0uAWa57xcw4COeR7gHq56P6")
                .build();

    }

    public static void upload(String key, InputStream inputStream, String contentType) {
        try {

            System.out.println(inputStream.available());

            minioClient.putObject(PutObjectArgs.builder()
                    .stream(inputStream, inputStream.available(), 100 * 1024 * 1024)
                    .object(key)
                    .bucket(bucket)
                    .contentType(contentType)
                    .build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generateKeyWithUUIDName(String category, String originFileName) {
        if (StringUtils.isEmpty(category)) {
            throw new IllegalArgumentException("s3 generate key parameter 'category' is required");
        }
        if (isInvalidFileName(originFileName)) {
            throw new IllegalArgumentException("s3 generate key parameter 'originFileName' is invalid");
        }

        String keyName = UUID.randomUUID().toString();
        String originFileExtension = getFileExtension(originFileName);
        if (!StringUtils.isEmpty(originFileExtension)) {
            keyName = keyName + "." + originFileExtension;
        }

        return generateKey(category, keyName);
    }

    private static boolean isInvalidFileName(String fileName) {
        return StringUtils.isEmpty(fileName) || INVALID_FILE_NAME_REG.matcher(fileName).find();
    }

    private static String getFileExtension(String fileName) {
        int extensionPos = fileName.lastIndexOf(".");
        return extensionPos == -1 ? "" : fileName.substring(extensionPos + 1);
    }

    private static String generateKey(String category, String fileName) {
        return String.format("%s/%s/%s", category, KEY_DATE_FORMAT.format(LocalDateTime.now()), fileName);
    }
}
