package com.program.projectquotation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.program.projectquotation.pojo.ProductDetail;
import com.program.projectquotation.service.ProductDetailService;
import com.program.projectquotation.mapper.ProductDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【product_detail(商品详情表（存储商品详情界面展示的多张图片）)】的数据库操作Service实现
 * @createDate 2024-10-01 17:19:54
 */
@Service
public class ProductDetailServiceImpl extends ServiceImpl<ProductDetailMapper, ProductDetail>
        implements ProductDetailService {

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Override
    public List<String> getProductDetails(int productId) {
        LambdaQueryWrapper<ProductDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductDetail::getProductId, productId);
        try {
            List<ProductDetail> productDetails = productDetailMapper.selectList(wrapper);
            if (productDetails == null || productDetails.isEmpty()) {
                return null;
            }
            List<String> res = new ArrayList<>();
            for (ProductDetail productDetail : productDetails) {
                res.add(productDetail.getProductDetail());
            }
            return res;
        } catch (Exception e) {
            log.error("get ProductDetails error", e);
            return null;
        }
    }
}




