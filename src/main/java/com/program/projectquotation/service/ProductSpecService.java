package com.program.projectquotation.service;

import com.program.projectquotation.pojo.ProductSpec;
import com.baomidou.mybatisplus.extension.service.IService;
import com.program.projectquotation.result.Result;

import java.util.List;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【product_spec(商品规格表)】的数据库操作Service
* @createDate 2024-10-01 17:19:54
*/
public interface ProductSpecService extends IService<ProductSpec> {
    public Map<String,String> getProductSpec(int productId);

    public Result createProductSpec(ProductSpec productSpec);

    public Result updateProductSpec(ProductSpec productSpec,String oldSpecName);

    public Result deleteProductSpec(int productId,String productSpecName);

    //批量删除
    public Result deleteProductSpecBatch(int productId);
}
