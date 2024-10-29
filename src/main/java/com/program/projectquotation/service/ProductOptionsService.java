package com.program.projectquotation.service;

import com.program.projectquotation.pojo.ProductOptions;
import com.baomidou.mybatisplus.extension.service.IService;
import com.program.projectquotation.result.Result;

import java.util.List;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【product_options(商品自定义选项)】的数据库操作Service
* @createDate 2024-10-01 17:19:54
*/
public interface ProductOptionsService extends IService<ProductOptions> {
    public List<Map<String,String>> getProductOptions(int productId);

    public Result createProductOptions(ProductOptions productOptions);

    public Result updateProductOptions(ProductOptions productOptions,String oldOptionName);

    public Result deleteProductOptions(int productId,String productOptionName);

    public Result deleteProductOptionsBatch(int productId);
}
