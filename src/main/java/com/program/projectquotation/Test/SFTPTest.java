package com.program.projectquotation.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import static com.program.projectquotation.utils.SSHUtils.sftp;

/**
 * Created by WHY on 2024/10/1.
 * Functions:
 */
public class SFTPTest {
    public static void main(String[] args) {
        try {
            // 从资源文件夹中读取图片文件
            ClassLoader classLoader = SFTPTest.class.getClassLoader();
            URL resource = classLoader.getResource("static/images/Flink.jpg");
            System.out.println(resource);
            File file = new File(resource.getFile());

            // 将文件转换为字节数组
            byte[] fileBytes = new byte[(int) file.length()];
            try (InputStream inputStream = new FileInputStream(file)) {
                inputStream.read(fileBytes);
            }

            // 调用SFTP上传方法
            sftp(fileBytes, "Flink.jpg","images");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
