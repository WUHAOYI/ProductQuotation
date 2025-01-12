package com.program.projectquotation.service;

import com.program.projectquotation.pojo.AllowedUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.program.projectquotation.result.Result;

import java.util.List;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【allowed_user】的数据库操作Service
* @createDate 2025-01-11 12:51:12
*/
public interface AllowedUserService extends IService<AllowedUser> {
    // 新增允许登录的用户
    public Result addAllowedUser(AllowedUser allowedUser);

    //批量新增允许登录的用户
    public Result addAllowedUserList(List<AllowedUser> allowedUserList);

    // 查询用户是否被允许登录
    public boolean isAllowedUser(String username);

    // 根据username查询已授权的用户
    public Result getAllowedUserByUsername(String username);

    // 查看所有已授权用户
    public Result getAllowedUser();

    // 删除已授权用户
    public Result deleteAllowedUser(String username);
}
