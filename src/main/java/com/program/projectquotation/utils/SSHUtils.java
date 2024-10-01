package com.program.projectquotation.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.program.projectquotation.common.StaticParamsCommon;

import java.io.OutputStream;

public class SSHUtils {
    /**
     * 文件上传
     * @param fileBytes 文件字节数组
     * @param fileName 文件名
     * @throws Exception
     */
    public static void sftp(byte[] fileBytes,String fileName, String type) throws Exception{
        //获取配置文件信息
        String ip = StaticParamsCommon.IP;
        String username = StaticParamsCommon.USERNAME;
        String privateKey = StaticParamsCommon.PRIVATE_KEY;
        int port = StaticParamsCommon.PORT;
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
        channel.connect(1000); //设置超时时间
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
}
