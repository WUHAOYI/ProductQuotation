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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @description 针对表【product_spec(商品规格表)】的数据库操作Service实现
 * @createDate 2024-10-01 17:19:54
 */
@Service
@Transactional
public class ProductSpecServiceImpl extends ServiceImpl<ProductSpecMapper, ProductSpec>
        implements ProductSpecService {

    @Autowired
    private ProductSpecMapper productSpecMapper;

    @Override
    public List<Map<String,String>> getProductSpec(int productId) {
        LambdaQueryWrapper<ProductSpec> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSpec::getProductId, productId);
        List<Map<String,String>> res = new ArrayList<>();
        try {
            List<ProductSpec> productSpecs = productSpecMapper.selectList(wrapper);
            if (productSpecs == null || productSpecs.isEmpty()) {
                return null;
            }
            for (ProductSpec productSpec : productSpecs) {
                Map<String, String> children = new HashMap<>();
                children.put("name",productSpec.getProductSpecName());
                children.put("value",productSpec.getProductSpecValue());
                res.add(children);
            }
            return res;
        } catch (Exception e) {
            log.error("get ProductSpec error", e);
            return null;
        }
    }

    /**
     * 批量创建商品规格信息
     *
     * @param productSpecs
     * @return
     */
    @Override
    public Result createProductSpec(List<ProductSpec> productSpecs) {
        try {
            boolean saved = saveBatch(productSpecs);
            if (!saved) {
                return Result.build(null, ResultCodeEnum.CREATE_PRODUCT_SPEC_ERROR);
            }
            return Result.build(null, ResultCodeEnum.CREATE_PRODUCT_SPEC_SUCCESS);
        } catch (Exception e) {
            log.error("create Product Specs error", e);
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
            System.out.println("delete productSpec success");
            return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_SPEC_SUCCESS);
        } catch (Exception e) {
            log.error("delete Product Specs error when delete product", e);
            return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_SPEC_ERROR);
        }
    }

    /**
     * 获取最高最低规格信息
     * @param productId
     * @return
     */
    @Override
    public Map<String, String> getMaxMinSpec(int productId) {
        LambdaQueryWrapper<ProductSpec> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSpec::getProductId, productId);
        wrapper.orderByAsc(ProductSpec::getProductSpecValue);
        try {
            List<ProductSpec> productSpecs = productSpecMapper.selectList(wrapper);
            if (productSpecs == null || productSpecs.isEmpty()) {
                return null;
            }
            Map<String, String> res = new HashMap<>();
            res.put("min", productSpecs.get(0).getProductSpecValue());
            res.put("max", productSpecs.get(productSpecs.size() - 1).getProductSpecValue());
            return res;
        } catch (Exception e) {
            log.error("get Max Min Spec error", e);
            return null;
        }
    }
}




