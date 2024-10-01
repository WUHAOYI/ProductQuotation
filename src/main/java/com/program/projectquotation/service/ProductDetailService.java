package com.program.projectquotation.service;

import com.program.projectquotation.pojo.ProductDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【product_detail(商品详情表（存储商品详情界面展示的多张图片）)】的数据库操作Service
* @createDate 2024-10-01 17:19:54
*/
public interface ProductDetailService extends IService<ProductDetail> {
    public List<String> getProductDetails(int productId);
}
