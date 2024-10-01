package com.program.projectquotation.service;

import com.program.projectquotation.pojo.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.program.projectquotation.result.Result;

/**
 * @author Administrator
 * @description 针对表【product(商品表)】的数据库操作Service
 * @createDate 2024-10-01 16:37:30
 */
public interface ProductService extends IService<Product> {
    public Result getProducts(int page, int size);

    public Result getProductsById(int categoryId, int page, int size);

    public Product getProductDetail(int productId);

}
