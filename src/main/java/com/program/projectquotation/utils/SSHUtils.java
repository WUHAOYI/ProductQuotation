package com.program.projectquotation.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.program.projectquotation.common.StaticParamsCommon;

import java.io.OutputStream;

public class SSHUtils {

    // 获取配置文件信息
    private static  String  ip = StaticParamsCommon.IP;
    private static  String username = StaticParamsCommon.USERNAME;
    private static  String privateKey = StaticParamsCommon.PRIVATE_KEY;
    private static  int port = StaticParamsCommon.PORT;

    /**
     * 文件上传
     * @param fileBytes 文件字节数组
     * @param fileName 文件名
     * @param type 文件类型（文件路径的一部分）
     * @throws Exception
     */
    public static void sftp(byte[] fileBytes,String fileName, String type) throws Exception{
        String filePath = StaticParamsCommon.FILE_PATH + type;

        JSch jsch = new JSch();
        // 使用 PEM 文件作为私钥
        jsch.addIdentity(privateKey);

        //创建session连接
        Session session = jsch.getSession(username, ip ,port);
        if (session == null) {
            throw new Exception("session create error");
        }
//        session.setPassword(password);//设置密码
        session.setConfig("StrictHostKeyChecking", "no"); //设置登陆提示为"no"
        session.connect(1000); //设置超时时间

        //创建通信通道
        Channel channel = (Channel) session.openChannel("sftp");
        if (channel == null)
        {
            throw new Exception("channel create error");
        }
        channel.connect(10000); //设置超时时间
        ChannelSftp sftp = (ChannelSftp) channel; //创建sftp通道

        OutputStream outputStream = null;
        //开始文件上传
        try {
            sftp.cd(filePath); //进入指定文件路径
            outputStream =sftp.put(fileName);
            outputStream.write(fileBytes);
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("file upload failed");
        } finally {
            if(outputStream != null){ //关闭文件流
                outputStream.flush();
                outputStream.close();
            }
            if(channel != null){ //关闭通道
                channel.disconnect();
            }
            if(session != null){ //关闭链接
                session.disconnect();
            }

        }
    }


    /**
     * 文件删除
     * @param fileName 文件名
     * @param type 文件类型（文件路径的一部分）
     * @throws Exception
     */
    public static void deleteFile(String fileName, String type) throws Exception {
        String filePath = StaticParamsCommon.FILE_PATH + type;
        JSch jsch = new JSch();

        // 使用 PEM 文件作为私钥
        jsch.addIdentity(privateKey);

        // 创建 session 连接
        Session session = jsch.getSession(username, ip, port);
        if (session == null) {
            throw new Exception("session create error");
        }
        session.setConfig("StrictHostKeyChecking", "no"); // 设置登陆提示为"no"
        session.connect(10000); // 设置超时时间

        // 创建通信通道
        Channel channel = session.openChannel("sftp");
        if (channel == null) {
            throw new Exception("channel create error");
        }
        channel.connect(10000); // 设置超时时间
        ChannelSftp sftp = (ChannelSftp) channel; // 创建 sftp 通道

        // 开始删除文件
        try {
            sftp.cd(filePath); // 进入指定文件路径
            sftp.rm(fileName); // 删除文件
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("file deletion failed");
        } finally {
            if (channel != null) { // 关闭通道
                channel.disconnect();
            }
            if (session != null) { // 关闭链接
                session.disconnect();
            }
        }
    }
}
