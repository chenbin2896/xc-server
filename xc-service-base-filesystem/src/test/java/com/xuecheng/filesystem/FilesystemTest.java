package com.xuecheng.filesystem;

import com.qiniu.util.IOUtils;
import com.xuecheng.filesystem.service.FileSystemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@SpringBootTest
public class FilesystemTest {

    @Autowired
    private FileSystemService fileSystemService;

    @Test
    void test () throws Exception {

        File file = new File("C:\\Users\\Administrator\\Desktop\\test1.ftl");

        InputStream inputStream = new FileInputStream(file);

        byte[] bytes1 = IOUtils.toByteArray(inputStream);

        MockMultipartFile mockMultipartFile = new MockMultipartFile(file.getName(), bytes1);

        System.out.println(mockMultipartFile.getOriginalFilename());
        System.out.println(mockMultipartFile.getName());

        fileSystemService.upload(mockMultipartFile, "file-test", "xuecheng", null);

    }
}
