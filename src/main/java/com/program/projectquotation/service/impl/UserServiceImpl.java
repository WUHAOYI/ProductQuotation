package com.program.projectquotation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.program.projectquotation.pojo.User;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.result.ResultCodeEnum;
import com.program.projectquotation.service.UserService;
import com.program.projectquotation.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
* @author Administrator
* @description 针对表【user(用户表（只能更新，不能新增）)】的数据库操作Service实现
* @createDate 2024-09-14 21:39:47
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result getUserInfo() {
        try {
            User user = userMapper.selectOne(null);
            if (!Objects.isNull(user))
                return Result.build(user, ResultCodeEnum.GET_USER_SUCCESS);
            return Result.build(null, ResultCodeEnum.GET_USER_ERROR);
        }catch (Exception e){
            throw new RuntimeException("获取用户信息失败");
        }
    }
}




