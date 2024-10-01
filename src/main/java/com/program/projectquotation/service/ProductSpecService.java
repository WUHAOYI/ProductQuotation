package com.program.projectquotation.service;

import com.program.projectquotation.pojo.ProductSpec;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author Administrator
* @description 针对表【product_spec(商品规格表)】的数据库操作Service
* @createDate 2024-10-01 17:19:54
*/
public interface ProductSpecService extends IService<ProductSpec> {
    public Map<String,String> getProductSpec(int productId);
}
