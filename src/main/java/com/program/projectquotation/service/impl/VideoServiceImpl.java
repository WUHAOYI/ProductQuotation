package com.program.projectquotation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.program.projectquotation.pojo.Product;
import com.program.projectquotation.pojo.Video;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.result.ResultCodeEnum;
import com.program.projectquotation.service.VideoService;
import com.program.projectquotation.mapper.VideoMapper;
import com.program.projectquotation.utils.SSHUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

/**
* @author Administrator
* @description 针对表【video(视频表)】的数据库操作Service实现
* @createDate 2024-10-01 12:13:15
*/
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
    implements VideoService{

    @Autowired
    private VideoMapper videoMapper;

    /**
     * 获取全部视频
     * @return
     */
    @Override
    public Result getVideos(int page, int size) {
        try {
            Page<Video> thisPage = new Page<>(page, size);
            LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByDesc(Video::getCreateTime); // 按创建时间倒序
            Page<Video> videoPage = videoMapper.selectPage(thisPage, wrapper);
            List<Video> videos = videoPage.getRecords();
            Map<String, Object> res = new HashMap<>();
            res.put("videos", videos);
            long total = videoMapper.selectCount(null);
            res.put("total", total);
            if (Objects.isNull(videos) || videos.isEmpty()) {
                return Result.build(null, ResultCodeEnum.VIDEO_NOT_FOUND);
            }
            return Result.build(res, ResultCodeEnum.GET_VIDEO_SUCCESS);
        } catch (Exception e) {
            log.error("getVideos error", e);
            return Result.build(null, ResultCodeEnum.GET_VIDEO_ERROR);
        }


    }

    @Override
    public Result uploadVideo(String id, String link) {
        try {
            int insert = videoMapper.insert(new Video(id, link));
            if (insert == 1) {
                return Result.build(ResultCodeEnum.UPLOAD_VIDEO_SUCCESS);
            }
            return Result.build(ResultCodeEnum.UPLOAD_VIDEO_ERROR);
        } catch (Exception e) {
            log.error("uploadVideo error", e);
            return Result.build(ResultCodeEnum.UPLOAD_VIDEO_ERROR);
        }
    }

    @Override
    public Result deleteVideo(int id) {
        try {
            deleteVideoFile(id);
        } catch (Exception e) {
            return Result.build(null, 50023, "无法删除视频文件");
        }

        try {
            int update = videoMapper.deleteById(id);
            if (update == 0) {
                return Result.build(null, ResultCodeEnum.VIDEO_NOT_FOUND);
            }
            return Result.build(null, ResultCodeEnum.DELETE_VIDEO_SUCCESS);
        } catch (Exception e) {
            log.error("delete video error", e);
            return Result.build(null, ResultCodeEnum.DELETE_VIDEO_ERROR);
        }
    }

    private void deleteVideoFile(int id)
    {
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<Video>().eq(Video::getVideoId, id);
        Video selected = videoMapper.selectOne(wrapper);
        String videoLink = selected.getVideoLink();
        String videoName = videoLink.split("//")[1].split("/")[1];
        try {
            SSHUtils.deleteFile(videoName, "videos");
        } catch (Exception e) {
            log.error("delete video error", e);
        }
    }


}




