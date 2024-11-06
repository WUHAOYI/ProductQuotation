package com.program.projectquotation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.program.projectquotation.pojo.Newdateuntilnow;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.result.ResultCodeEnum;
import com.program.projectquotation.service.NewdateuntilnowService;
import com.program.projectquotation.mapper.NewdateuntilnowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【newDateUntilNow】的数据库操作Service实现
 * @createDate 2024-10-29 20:22:38
 */
@Service
public class NewdateuntilnowServiceImpl extends ServiceImpl<NewdateuntilnowMapper, Newdateuntilnow>
        implements NewdateuntilnowService {
    @Autowired
    private NewdateuntilnowMapper newdateuntilnowMapper;

    /**
     * 获取上新时间
     * @return
     */
    @Override
    public int getNewdateuntilnow() {
        try {
            return newdateuntilnowMapper.selectOne(null).getNewdateuntilnow();
        } catch (Exception e) {
            log.error("get Newdateuntilnow error", e);
            return -1;
        }
    }

    /**
     * 修改上新时间
     * @param time
     * @return
     */
    @Override
    public Result updateNewdateuntilnow(int time) {
        try {
            Newdateuntilnow newdateuntilnow = newdateuntilnowMapper.selectOne(null);
            newdateuntilnow.setNewdateuntilnow(time);
            int update = newdateuntilnowMapper.update(newdateuntilnow, null);
            if (update > 0) {
                return Result.build(null, ResultCodeEnum.TIME_UPDATE_SUCCESS);
            }
            return Result.build(null, ResultCodeEnum.UPDATE_USER_ERROR);

        } catch (Exception e) {
            log.error("update Newdateuntilnow error", e);
            return Result.build(null, ResultCodeEnum.UPDATE_USER_ERROR);
        }
    }
}




