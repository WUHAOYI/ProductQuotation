package com.program.projectquotation.controller;

import com.program.projectquotation.pojo.Product;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.result.ResultCodeEnum;
import com.program.projectquotation.service.ProductDetailService;
import com.program.projectquotation.service.ProductOptionsService;
import com.program.projectquotation.service.ProductService;
import com.program.projectquotation.service.ProductSpecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WHY on 2024/10/1.
 * Functions:
 */
@RestController
@RequestMapping("product")
@CrossOrigin
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductSpecService productSpecService;
    @Autowired
    private ProductOptionsService productOptionsService;

    /**
     * 获取所有产品
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/all")
    public Result getProducts(@RequestParam("page") int page,
                              @RequestParam("size") int size) {
        return productService.getProducts(page, size);
    }

    /**
     * 根据目录id获取产品
     *
     * @param categoryId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/category")
    public Result getProductsById(@RequestParam("categoryId") int categoryId,
                                  @RequestParam("page") int page,
                                  @RequestParam("size") int size) {
        return productService.getProductsById(categoryId, page, size);
    }

    @GetMapping("{id}")
    @Transactional
    public Result getProductDetail(@PathVariable("id") int productId) {
        Map<String, Object> res = new HashMap<>();
        try {
            Product product = productService.getProductDetail(productId);
            List<String> productDetails = productDetailService.getProductDetails(productId);
            Map<String, String> productSpecs = productSpecService.getProductSpec(productId);
            Map<String, String> productOptions = productOptionsService.getProductOptions(productId);
            res.put("id", product.getId());
            res.put("name", product.getProductName());
            res.put("lowPrice", product.getProductLowPrice());
            res.put("highPrice", product.getProductHighPrice());
            res.put("description", product.getProductIntro());
            res.put("images", productDetails);
            res.put("specs", productSpecs);
            res.put("options", productOptions);
            return Result.build(res, ResultCodeEnum.GET_PRODUCT_DETAIL_SUCCESS);
        } catch (Exception e) {
            log.error("get ProductDetails error", e);
            return Result.build(null, ResultCodeEnum.GET_PRODUCT_DETAIL_ERROR);
        }
    }


}
