package com.program.projectquotation.controller;

import com.program.projectquotation.common.StaticParamsCommon;
import com.program.projectquotation.pojo.AllowedUser;
import com.program.projectquotation.pojo.User;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.result.ResultCodeEnum;
import com.program.projectquotation.service.AllowedUserService;
import com.program.projectquotation.service.UserService;
import com.program.projectquotation.utils.LocalFileUtils;
import com.program.projectquotation.utils.SSHUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.*;

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

    @Autowired
    private AllowedUserService allowedUserService;

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

    //新增允许登录的用户
    @PostMapping("allowedUser")
    public Result addAllowedUser(@RequestBody AllowedUser allowedUser) {
        if (allowedUserService.isAllowedUser(allowedUser.getUsername())) {
            return Result.build(null, 50025, "用户已经存在");
        }
        return allowedUserService.addAllowedUser(allowedUser);
    }

    //批量新增允许登录的用户
    @PostMapping("allowedUserList")
    public Result addAllowedUserList(@RequestBody List<AllowedUser> allowedUserList) {
        return allowedUserService.addAllowedUserList(allowedUserList);
    }

    //根据username查询已授权的用户
    @GetMapping("allowedUser/{username}")
    public Result getAllowedUserByUsername(@PathVariable("username") String username) {
        return allowedUserService.getAllowedUserByUsername(username);
    }

    //查询所有已授权用户
    @GetMapping("allowedUser")
    public Result getAllowedUser() {
        return allowedUserService.getAllowedUser();
    }

    //删除已授权用户
    @DeleteMapping("allowedUser/{username}")
    public Result deleteAllowedUser(@PathVariable("username") String username) {
        return allowedUserService.deleteAllowedUser(username);
    }

    // 查看用户是否有登录权限
    @GetMapping("checkAuth")
    public Result wxLogin(@RequestParam("username") String username) {
        if (allowedUserService.isAllowedUser(username)) {
            return Result.build(true, 200,"允许用户登录");
        }
        return Result.build(false, 401,"用户未授权");
    }
}
