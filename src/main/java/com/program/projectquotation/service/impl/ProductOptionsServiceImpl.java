package com.program.projectquotation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.program.projectquotation.pojo.ProductOptions;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.result.ResultCodeEnum;
import com.program.projectquotation.service.ProductOptionsService;
import com.program.projectquotation.mapper.ProductOptionsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @description 针对表【product_options(商品自定义选项)】的数据库操作Service实现
 * @createDate 2024-10-01 17:19:54
 */
@Service
public class ProductOptionsServiceImpl extends ServiceImpl<ProductOptionsMapper, ProductOptions>
        implements ProductOptionsService {

    @Autowired
    private ProductOptionsMapper productOptionsMapper;

    @Override
    public List<Map<String, String>> getProductOptions(int productId) {
        LambdaQueryWrapper<ProductOptions> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductOptions::getProductId, productId);
        List<Map<String, String>> res = new ArrayList<>();
        try {
            List<ProductOptions> productOptions = productOptionsMapper.selectList(wrapper);
            if (productOptions == null || productOptions.isEmpty()) {
                return null;
            }
            for (ProductOptions productOption : productOptions) {
                Map<String, String> children = new HashMap<>();
                children.put("name", productOption.getProductOptionName());
                children.put("info", productOption.getProductOptionInfo());
                res.add(children);
            }
            return res;
        } catch (Exception e) {
            log.error("get ProductOptions error", e);
            return null;
        }


    }

    /**
     * 创建商品自定义选项
     *
     * @param productOptions
     * @return
     */
    @Override
    public Result createProductOptions(ProductOptions productOptions) {
        try {
            int insert = productOptionsMapper.insert(productOptions);
            if (insert == 0) {
                return Result.build(null, ResultCodeEnum.CREATE_PRODUCT_OPTIONS_ERROR);
            }
            return Result.build(null, ResultCodeEnum.CREATE_PRODUCT_OPTIONS_SUCCESS);
        } catch (Exception e) {
            log.error("create ProductOptions error", e);
            return Result.build(null, ResultCodeEnum.CREATE_PRODUCT_OPTIONS_ERROR);
        }
    }

    /**
     * 更新商品自定义选项
     * @param productOptions
     * @param oldOptionName
     * @return
     */
    @Override
    public Result updateProductOptions(ProductOptions productOptions, String oldOptionName) {
        try {
            LambdaUpdateWrapper<ProductOptions> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(ProductOptions::getProductId, productOptions.getProductId());
            wrapper.eq(ProductOptions::getProductOptionName, oldOptionName);
            int update = productOptionsMapper.update(productOptions, wrapper);
            if (update == 0) {
                return Result.build(null, ResultCodeEnum.UPDATE_PRODUCT_OPTIONS_ERROR);
            }
            return Result.build(null, ResultCodeEnum.UPDATE_PRODUCT_OPTIONS_SUCCESS);
        } catch (Exception e) {
            log.error("update ProductOptions error", e);
            return Result.build(null, ResultCodeEnum.UPDATE_PRODUCT_OPTIONS_ERROR);
        }
    }

    /**
     * 删除商品自定义选项
     * @param productId
     * @param productOptionName
     * @return
     */
    @Override
    public Result deleteProductOptions(int productId, String productOptionName) {
        try {
            LambdaQueryWrapper<ProductOptions> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ProductOptions::getProductId, productId);
            wrapper.eq(ProductOptions::getProductOptionName, productOptionName);
            int delete = productOptionsMapper.delete(wrapper);
            if (delete == 0) {
                return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_OPTIONS_ERROR);
            }
            return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_OPTIONS_SUCCESS);
        } catch (Exception e) {
            log.error("delete ProductOptions error", e);
            return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_OPTIONS_ERROR);
        }
    }

    /**
     * 批量删除商品自定义选项
     * @param productId
     * @return
     */
    @Override
    public Result deleteProductOptionsBatch(int productId) {
        LambdaQueryWrapper<ProductOptions> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductOptions::getProductId, productId);
        try {
            boolean remove = remove(wrapper);
            if (!remove) {
                return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_OPTIONS_ERROR);
            }
            return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_OPTIONS_SUCCESS);
        } catch (Exception e) {
            log.error("delete ProductOptions error when delete product", e);
            return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_OPTIONS_ERROR);
        }
    }
}




