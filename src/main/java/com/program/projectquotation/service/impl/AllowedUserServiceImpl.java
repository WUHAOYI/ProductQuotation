package com.program.projectquotation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.program.projectquotation.pojo.AllowedUser;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.result.ResultCodeEnum;
import com.program.projectquotation.service.AllowedUserService;
import com.program.projectquotation.mapper.AllowedUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @description 针对表【allowed_user】的数据库操作Service实现
 * @createDate 2025-01-11 12:51:12
 */
@Service
public class AllowedUserServiceImpl extends ServiceImpl<AllowedUserMapper, AllowedUser>
        implements AllowedUserService {

    @Autowired
    private AllowedUserMapper allowedUserMapper;

    @Override
    public Result addAllowedUser(AllowedUser allowedUser) {
        try {
            int insert = allowedUserMapper.insert(allowedUser);
            if (insert > 0)
                return Result.build(null, ResultCodeEnum.ADD_ALLOWED_USER_SUCCESS);
            return Result.build(null, ResultCodeEnum.ADD_ALLOWED_USER_ERROR);
        } catch (Exception e) {
            log.error("新增允许登录的用户失败");
            return Result.build(null, ResultCodeEnum.ADD_ALLOWED_USER_ERROR);
        }
    }

    @Override
    public Result addAllowedUserList(List<AllowedUser> allowedUserList) {
        int successCount = 0;
        int failureCount = 0;

        try{
            for (AllowedUser user : allowedUserList) {
                // 检查是否已存在相同的用户名
                QueryWrapper<AllowedUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username", user.getUsername());

                AllowedUser existingUser = this.getOne(queryWrapper);
                if (existingUser == null) {
                    // 不存在相同的用户名，则插入记录
                    boolean success = this.save(user);
                    if (success) {
                        successCount++;
                    }
                } else {
                    // 存在相同的用户名，插入失败
                    failureCount++;
                }
            }
            return Result.build(Map.of("successCount", successCount, "failureCount", failureCount,"sumCount",successCount+failureCount), ResultCodeEnum.ADD_ALLOWED_USER_BATCH_SUCCESS);
        }catch (Exception e){
            log.error("批量新增允许登录的用户失败");
            return Result.build(null, ResultCodeEnum.ADD_ALLOWED_USER_BATCH_ERROR);
        }
    }


    @Override
    public boolean isAllowedUser(String username) {
        // 根据username是否存在来判定用户是否被允许登录
        LambdaUpdateWrapper<AllowedUser> allowedUserLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        allowedUserLambdaUpdateWrapper.eq(AllowedUser::getUsername, username);
        AllowedUser allowedUser = allowedUserMapper.selectOne(allowedUserLambdaUpdateWrapper);
        return allowedUser != null;
    }

    @Override
    public Result getAllowedUserByUsername(String username) {
        LambdaUpdateWrapper<AllowedUser> allowedUserLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        allowedUserLambdaUpdateWrapper.like(AllowedUser::getUsername, username);
        try {
            List<AllowedUser> allowedUserList = allowedUserMapper.selectList(allowedUserLambdaUpdateWrapper);
            if (!allowedUserList.isEmpty())
                return Result.build(allowedUserList, ResultCodeEnum.GET_ALLOWED_USER_SUCCESS);
            else return Result.build(null, ResultCodeEnum.ALLOWED_USER_NOT_FOUND);
        } catch (Exception e) {
            log.error("根据用户名查询已授权的用户失败");
            return Result.build(null, ResultCodeEnum.GET_ALLOWED_USER_ERROR);
        }
    }

    @Override
    public Result getAllowedUser() {
        try {
            return Result.build(allowedUserMapper.selectList(null), ResultCodeEnum.GET_ALLOWED_USER_SUCCESS);
        } catch (Exception e) {
            log.error("查询所有已授权用户失败");
            return Result.build(null, ResultCodeEnum.GET_ALLOWED_USER_ERROR);
        }
    }

    @Override
    public Result deleteAllowedUser(String username) {
        LambdaUpdateWrapper<AllowedUser> allowedUserLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        allowedUserLambdaUpdateWrapper.eq(AllowedUser::getUsername, username);
        try {
            int delete = allowedUserMapper.delete(allowedUserLambdaUpdateWrapper);
            if (delete > 0)
                return Result.build(null, ResultCodeEnum.DELETE_ALLOWED_USER_SUCCESS);
            return Result.build(null, ResultCodeEnum.DELETE_ALLOWED_USER_ERROR);
        } catch (Exception e) {
            log.error("删除已授权用户失败");
            return Result.build(null, ResultCodeEnum.DELETE_ALLOWED_USER_ERROR);
        }
    }
}




