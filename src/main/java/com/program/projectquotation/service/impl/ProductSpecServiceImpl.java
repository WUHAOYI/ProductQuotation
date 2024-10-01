package com.program.projectquotation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.program.projectquotation.pojo.ProductSpec;
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
}




