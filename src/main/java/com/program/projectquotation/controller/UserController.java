package com.program.projectquotation.controller;

import com.program.projectquotation.common.StaticParamsCommon;
import com.program.projectquotation.pojo.User;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.result.ResultCodeEnum;
import com.program.projectquotation.service.UserService;
import com.program.projectquotation.utils.LocalFileUtils;
import com.program.projectquotation.utils.SSHUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

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
    public Result getUserInfo() {
        return userService.getUserInfo();
    }

    @PutMapping()
    public Result updateUserInfo(@RequestParam("username") String username,
                                 @RequestPart(required = false) String fileName,
                                 @RequestPart(required = false) MultipartFile avatarFile,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("location") String location,
                                 @RequestParam("intro") String intro) {
        User user = new User();
        user.setUsername(username);
        user.setPhone(phone);
        user.setLocation(location);
        user.setIntro(intro);
        //允许不上传头像，只修改用户名
        if (!Objects.isNull(avatarFile) && !avatarFile.isEmpty()) {
            try {
                byte[] bytes = avatarFile.getBytes();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                UUID uuid = UUID.randomUUID();
                fileName = timestamp.getTime() + "-user-" + uuid + ".png";
                LocalFileUtils.saveToLocal(bytes, fileName, "images");
//                SSHUtils.sftp(bytes, fileName, "images");
                user.setAvatar(StaticParamsCommon.IMAGES_VIDEOS_PATH + fileName);
                return userService.updateUserInfo(user);
            } catch (Exception e) {
                log.error("updateUserInfo error", e);
                return Result.build(ResultCodeEnum.UPLOAD_VIDEO_ERROR);
            }
        }
        return userService.updateUserInfo(user);
    }

}
