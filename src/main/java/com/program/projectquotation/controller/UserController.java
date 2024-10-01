package com.program.projectquotation.controller;

import com.program.projectquotation.common.StaticParamsCommon;
import com.program.projectquotation.pojo.User;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.result.ResultCodeEnum;
import com.program.projectquotation.service.UserService;
import com.program.projectquotation.utils.SSHUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

/**
 * Created by WHY on 2024/9/14.
 * Functions:
 */
@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @GetMapping()
    public Result getUserInfo(){
        return userService.getUserInfo();
    }

    @PutMapping()
    public Result updateUserInfo(@RequestParam("username") String username,
                                 @RequestParam("fileName") String fileName,
                                 @RequestParam("avatarFile") MultipartFile avatarFile){
        if (avatarFile.isEmpty()) {
            return Result.build(ResultCodeEnum.UPLOAD_VIDEO_ERROR);
        }
        try {
            byte[] bytes = avatarFile.getBytes();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            fileName = timestamp.getTime() + "_" + fileName + ".png";
            SSHUtils.sftp(bytes, fileName, "images");
            User user = new User();
            user.setUsername(username);
            user.setAvatar(StaticParamsCommon.IMAGES_VIDEOS_PATH + fileName);
            return userService.updateUserInfo(user);
        } catch (Exception e) {
            log.error("updateUserInfo error", e);
            return Result.build(ResultCodeEnum.UPLOAD_VIDEO_ERROR);
        }
    }

}
