package com.program.projectquotation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.program.projectquotation.pojo.Product;
import com.program.projectquotation.pojo.Video;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.result.ResultCodeEnum;
import com.program.projectquotation.service.ProductService;
import com.program.projectquotation.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Administrator
 * @description 针对表【product(商品表)】的数据库操作Service实现
 * @createDate 2024-10-01 16:37:30
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
        implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    /**
     * 获取所有产品
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Result getProducts(int page, int size) {
        try {
            Page<Product> thisPage = new Page<>(page, size);

            Page<Product> productPage = productMapper.selectPage(thisPage, null);
            List<Product> products = productPage.getRecords();
            if (Objects.isNull(products) || products.isEmpty()) {
                return Result.build(null, ResultCodeEnum.PRODUCT_NOT_FOUND);
            }
            return Result.build(products, ResultCodeEnum.GET_PRODUCT_SUCCESS);
        } catch (Exception e) {
            log.error("get Products error", e);
            return Result.build(null, ResultCodeEnum.GET_PRODUCT_ERROR);
        }
    }

    /**
     * 根据目录id获取产品
     *
     * @param categoryId
     * @param page
     * @param size
     * @return
     */
    @Override
    public Result getProductsById(int categoryId, int page, int size) {
        try {
            Page<Product> thisPage = new Page<>(page, size);
            LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Product::getCategoryId, categoryId);
            Page<Product> productPage = productMapper.selectPage(thisPage, wrapper);
            List<Product> products = productPage.getRecords();
            if (Objects.isNull(products) || products.isEmpty()) {
                return Result.build(null, ResultCodeEnum.PRODUCT_NOT_FOUND);
            }
            return Result.build(products, ResultCodeEnum.GET_PRODUCT_SUCCESS);
        } catch (Exception e) {
            log.error("get Products error", e);
            return Result.build(null, ResultCodeEnum.GET_PRODUCT_ERROR);
        }
    }

    @Override
    public Product getProductDetail(int productId) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getId, productId);
        try {
            Product product = productMapper.selectOne(wrapper);
            if (Objects.isNull(product)) {
                return null;
            }
            return product;
        } catch (Exception e) {
            log.error("get ProductDetail error", e);
            return null;
        }
    }
}




