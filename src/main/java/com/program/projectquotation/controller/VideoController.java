package com.program.projectquotation.controller;

import com.program.projectquotation.common.StaticParamsCommon;
import com.program.projectquotation.pojo.Video;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.result.ResultCodeEnum;
import com.program.projectquotation.service.VideoService;
import com.program.projectquotation.utils.SSHUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by WHY on 2024/10/1.
 * Functions:
 */
@RestController
@RequestMapping("video")
@CrossOrigin
public class VideoController {
    private static final Logger log = LoggerFactory.getLogger(VideoController.class);
    @Autowired
    VideoService videoService;

    /**
     * 分页获取全部视频
     * @param page 页数
     * @param size 每页内容条数
     * @return
     */
    @GetMapping()
    public Result getVideos(@RequestParam("page") int page,
                            @RequestParam("size") int size) {
        return videoService.getVideos(page, size);
    }

    /**
     * 上传视频
     * @param videoName 视频名称
     * @param videoFile 视频文件
     * @return
     */
    @PostMapping()
    public Result uploadVideo(@RequestParam("fileName") String fileName,
                              @RequestParam("videoFile") MultipartFile videoFile) {
        if (videoFile.isEmpty()) {
            return Result.build(ResultCodeEnum.UPLOAD_VIDEO_ERROR);
        }
        try {
            byte[] bytes = videoFile.getBytes();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String fileLink = timestamp.getTime() + "_" + fileName + ".mp4";
            SSHUtils.sftp(bytes, fileName, "videos");
            return videoService.uploadVideo(fileName, StaticParamsCommon.IMAGES_VIDEOS_PATH + fileLink);
        } catch (Exception e) {
            log.error("getVideos error", e);
            return Result.build(ResultCodeEnum.UPLOAD_VIDEO_ERROR);
        }
    }
}
