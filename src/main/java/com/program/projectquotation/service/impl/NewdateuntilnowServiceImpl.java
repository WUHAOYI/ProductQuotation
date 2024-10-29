package com.program.projectquotation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.program.projectquotation.pojo.Newdateuntilnow;
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
    implements NewdateuntilnowService{
    @Autowired
    private NewdateuntilnowMapper newdateuntilnowMapper;

    @Override
    public int getNewdateuntilnow() {
        try {
            return newdateuntilnowMapper.selectOne(null).getNewdateuntilnow();
        }catch (Exception e) {
            log.error("get Newdateuntilnow error", e);
            return -1;
        }
    }
}




