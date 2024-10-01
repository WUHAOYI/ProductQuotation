package com.program.projectquotation.service;

import com.program.projectquotation.pojo.ProductOptions;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author Administrator
* @description 针对表【product_options(商品自定义选项)】的数据库操作Service
* @createDate 2024-10-01 17:19:54
*/
public interface ProductOptionsService extends IService<ProductOptions> {
    public Map<String,String> getProductOptions(int productId);
}
