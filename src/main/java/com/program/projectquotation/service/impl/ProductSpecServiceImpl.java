package com.program.projectquotation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.program.projectquotation.pojo.ProductSpec;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.result.ResultCodeEnum;
import com.program.projectquotation.service.ProductSpecService;
import com.program.projectquotation.mapper.ProductSpecMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @description 针对表【product_spec(商品规格表)】的数据库操作Service实现
 * @createDate 2024-10-01 17:19:54
 */
@Service
public class ProductSpecServiceImpl extends ServiceImpl<ProductSpecMapper, ProductSpec>
        implements ProductSpecService {

    @Autowired
    private ProductSpecMapper productSpecMapper;

    @Override
    public Map<String, String> getProductSpec(int productId) {
        LambdaQueryWrapper<ProductSpec> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSpec::getProductId, productId);
        HashMap<String, String> res = new HashMap<>();
        try {
            List<ProductSpec> productSpecs = productSpecMapper.selectList(wrapper);
            if (productSpecs == null || productSpecs.isEmpty()) {
                return null;
            }
            for (ProductSpec productSpec : productSpecs) {
                res.put(productSpec.getProductSpecName(), productSpec.getProductSpecValue());
            }
            return res;
        } catch (Exception e) {
            log.error("get ProductSpec error", e);
            return null;
        }
    }

    /**
     * 创建商品规格信息
     *
     * @param productSpec
     * @return
     */
    @Override
    public Result createProductSpec(ProductSpec productSpec) {

        try {
            int insert = productSpecMapper.insert(productSpec);
            if (insert == 0) {
                return Result.build(null, ResultCodeEnum.CREATE_PRODUCT_SPEC_ERROR);
            }
            return Result.build(null, ResultCodeEnum.CREATE_PRODUCT_SPEC_SUCCESS);
        } catch (Exception e) {
            log.error("create Product Spec error", e);
            return Result.build(null, ResultCodeEnum.CREATE_PRODUCT_SPEC_ERROR);
        }
    }

    @Override
    public Result updateProductSpec(ProductSpec productSpec, String oldSpecName) {
        //更新
        try {
            LambdaUpdateWrapper<ProductSpec> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(ProductSpec::getProductId, productSpec.getProductId());
            wrapper.eq(ProductSpec::getProductSpecName, oldSpecName);
            int update = productSpecMapper.update(productSpec, wrapper);
            if (update == 0) {
                return Result.build(null, ResultCodeEnum.UPDATE_PRODUCT_SPEC_ERROR);
            }
            return Result.build(null, ResultCodeEnum.UPDATE_PRODUCT_SPEC_SUCCESS);
        } catch (Exception e) {
            log.error("update Product Spec error", e);
            return Result.build(null, ResultCodeEnum.UPDATE_PRODUCT_SPEC_ERROR);
        }
    }

    @Override
    public Result deleteProductSpec(int productId ,String productSpecName) {
        try {
            LambdaQueryWrapper<ProductSpec> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ProductSpec::getProductId, productId);
            wrapper.eq(ProductSpec::getProductSpecName, productSpecName);
            int delete = productSpecMapper.delete(wrapper);
            if (delete == 0) {
                return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_SPEC_ERROR);
            }
            return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_SPEC_SUCCESS);
        } catch (Exception e) {
            log.error("delete Product Spec error", e);
            return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_SPEC_ERROR);
        }
    }

    /**
     * 批量删除商品规格信息
     * @param productId
     * @return
     */
    @Override
    public Result deleteProductSpecBatch(int productId) {
        LambdaQueryWrapper<ProductSpec> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSpec::getProductId, productId);
        try {
            boolean remove = remove(wrapper);
            if (!remove) {
                return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_SPEC_ERROR);
            }
            return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_SPEC_SUCCESS);
        } catch (Exception e) {
            log.error("delete Product Specs error when delete product", e);
            return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_SPEC_ERROR);
        }
    }
}




