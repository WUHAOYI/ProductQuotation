package com.program.projectquotation.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by WHY on 2024/11/21.
 * Functions: 本地文件操作工具类
 */
public class LocalFileUtils {

    /**
     * 文件上传到本地
     * @param fileBytes 文件字节数组
     * @param fileName 文件名
     * @param type 文件类型（文件路径的一部分）
     * @throws Exception
     */
    public static void saveToLocal(byte[] fileBytes, String fileName, String type) throws Exception {
        // 定义本地保存文件的路径
//        String basePath = "X:/JavaData/";
        String basePath = "D:/JavaData/";
        String filePath = basePath + type;

        // 创建目录（如果不存在）
        File directory = new File(filePath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new Exception("Failed to create directory: " + filePath);
            }
        }

        // 创建文件输出流写入文件
        File file = new File(filePath, fileName);
        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to save file locally");
        }
    }

    /**
     * 删除本地文件
     * @param fileName 文件名
     * @param type 文件类型（文件路径的一部分）
     * @throws Exception
     */
    public static void deleteLocalFile(String fileName, String type) throws Exception {
        // 定义本地文件路径
//        String basePath = "X:/JavaData/";
        String basePath = "D:/JavaData/";
        String filePath = basePath + type + "/" + fileName;

        File file = new File(filePath);
        if (file.exists()) {
            if (!file.delete()) {
                throw new Exception("Failed to delete file: " + filePath);
            }
        } else {
            throw new Exception("File does not exist: " + filePath);
        }
    }
}

