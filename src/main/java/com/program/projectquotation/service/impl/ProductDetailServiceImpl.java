package com.program.projectquotation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.program.projectquotation.pojo.ProductDetail;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.result.ResultCodeEnum;
import com.program.projectquotation.service.ProductDetailService;
import com.program.projectquotation.mapper.ProductDetailMapper;
import com.program.projectquotation.utils.LocalFileUtils;
import com.program.projectquotation.utils.SSHUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【product_detail(商品详情表（存储商品详情界面展示的多张图片）)】的数据库操作Service实现
 * @createDate 2024-10-01 17:19:54
 */
@Service
@Transactional
public class ProductDetailServiceImpl extends ServiceImpl<ProductDetailMapper, ProductDetail>
        implements ProductDetailService {

    @Autowired
    private ProductDetailMapper productDetailMapper;

    /**
     * 获取商品图片信息
     *
     * @param productId
     * @return
     */
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

    /**
     * 创建商品图片信息(可批量上传)
     *
     * @param productDetails
     * @return
     */
    @Override
    public Result uploadProductDetails(List<ProductDetail> productDetails) {
        try {
            boolean saveBatch = saveBatch(productDetails);
            if (!saveBatch) {
                return Result.build(null, ResultCodeEnum.CREATE_PRODUCT_IMAGE_ERROR);
            }
            return Result.build(null, ResultCodeEnum.CREATE_PRODUCT_IMAGE_SUCCESS);
        } catch (Exception e) {
            log.error("create product images error", e);
            return Result.build(null, ResultCodeEnum.CREATE_PRODUCT_IMAGE_ERROR);
        }
    }

    /**
     * 删除商品图片信息
     * @param productId
     * @param imageName
     * @return
     */
    @Override
    public Result deleteProductDetail(int productId, String imageName) {
        LambdaQueryWrapper<ProductDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductDetail::getProductId, productId);
        wrapper.eq(ProductDetail::getProductDetailName, imageName);
        try {
            boolean remove = remove(wrapper);
            if (!remove) {
                return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_IMAGE_ERROR);
            }
            return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_IMAGE_SUCCESS);
        } catch (Exception e) {
            log.error("delete product image error", e);
            return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_IMAGE_ERROR);
        }
    }

    /**
     * 批量删除商品图片信息
     * @param productId
     * @return
     */
    @Override
    public Result deleteProductDetailBatch(int productId) {
        LambdaQueryWrapper<ProductDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductDetail::getProductId, productId);
        //先从数据库中获取图片名称，然后删除图片
        productDetailMapper.selectList(wrapper).forEach(productDetail -> {
            String productDetailName = productDetail.getProductDetailName();
            try {
                LocalFileUtils.deleteLocalFile(productDetailName, "images");
//                SSHUtils.deleteFile(productDetailName, "images");
            } catch (Exception e) {
                log.error("delete images error when delete product", e);
            }
        });
        //删除数据库中的图片信息
        try {
            boolean remove = remove(wrapper);
            if (!remove) {
                return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_IMAGE_ERROR);
            }
            System.out.println("delete product images success");
            return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_IMAGE_SUCCESS);
        } catch (Exception e) {
            log.error("delete product images error when delete product", e);
            return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_IMAGE_ERROR);
        }
    }


}




