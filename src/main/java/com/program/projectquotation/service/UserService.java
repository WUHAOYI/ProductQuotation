package com.program.projectquotation.service;

import com.program.projectquotation.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.program.projectquotation.result.Result;

/**
* @author Administrator
* @description 针对表【user(用户表（只能更新，不能新增）)】的数据库操作Service
* @createDate 2024-09-14 21:39:47
*/
public interface UserService extends IService<User> {
    public Result getUserInfo();

    public Result updateUserInfo(User user);
}
