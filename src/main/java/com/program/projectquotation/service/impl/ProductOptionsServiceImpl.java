package com.program.projectquotation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.program.projectquotation.pojo.ProductOptions;
import com.program.projectquotation.service.ProductOptionsService;
import com.program.projectquotation.mapper.ProductOptionsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    implements ProductOptionsService{

    @Autowired
    private ProductOptionsMapper productOptionsMapper;

    @Override
    public Map<String, String> getProductOptions(int productId) {
        LambdaQueryWrapper<ProductOptions> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductOptions::getProductId, productId);
        HashMap<String, String> res = new HashMap<>();
        try {
            List<ProductOptions> productOptions = productOptionsMapper.selectList(wrapper);
            if (productOptions == null || productOptions.isEmpty()) {
                return null;
            }
            for (ProductOptions productOption : productOptions) {
                res.put(productOption.getProductOptionName(), productOption.getProductOptionInfo());
            }
            return res;
        } catch (Exception e) {
            log.error("get ProductOptions error", e);
            return null;
        }


    }
}




