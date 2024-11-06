package com.program.projectquotation.service;

import com.program.projectquotation.pojo.Newdateuntilnow;
import com.baomidou.mybatisplus.extension.service.IService;
import com.program.projectquotation.result.Result;

/**
* @author Administrator
* @description 针对表【newDateUntilNow】的数据库操作Service
* @createDate 2024-10-29 20:22:38
*/
public interface NewdateuntilnowService extends IService<Newdateuntilnow> {
    public int getNewdateuntilnow();

    public Result updateNewdateuntilnow(int time);
}
