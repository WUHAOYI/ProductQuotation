package com.program.projectquotation.service;

import com.program.projectquotation.pojo.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import com.program.projectquotation.result.Result;

import java.util.Map;

/**
 * @author Administrator
 * @description 针对表【video(视频表)】的数据库操作Service
 * @createDate 2024-10-01 12:13:15
 */
public interface VideoService extends IService<Video> {
    public Result getVideos(int page, int size);

    public Result uploadVideo(String id, String link);

    public Result deleteVideo(int id);
}
