package com.program.projectquotation.service;

import com.program.projectquotation.pojo.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.program.projectquotation.result.Result;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【product(商品表)】的数据库操作Service
 * @createDate 2024-10-01 16:37:30
 */
public interface ProductService extends IService<Product> {
    public Result getProducts(int page, int size,int newFlag);

    public List<Product> getProductsByCategoryId(int categoryId);

    public Result getProductsById(int categoryId, int page, int size,int newFlag);

    public Result getProductsByName(String productName, int page, int size,int newFlag);

    public Result getProductsByIdName(int categoryId, String productName, int page, int size,int newFlag);

    public Product getProductDetail(int productId);

    public Result createProduct(Product product);

    public Result updateProduct(Product product,Boolean deleteFlag);

    public Result deleteProduct(int productId);
}
