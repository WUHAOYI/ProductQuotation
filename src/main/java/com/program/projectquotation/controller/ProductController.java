package com.program.projectquotation.controller;

import com.program.projectquotation.common.StaticParamsCommon;
import com.program.projectquotation.pojo.Product;
import com.program.projectquotation.pojo.ProductDetail;
import com.program.projectquotation.pojo.ProductOptions;
import com.program.projectquotation.pojo.ProductSpec;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.result.ResultCodeEnum;
import com.program.projectquotation.service.ProductDetailService;
import com.program.projectquotation.service.ProductOptionsService;
import com.program.projectquotation.service.ProductService;
import com.program.projectquotation.service.ProductSpecService;
import com.program.projectquotation.utils.SSHUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

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
//    @GetMapping("/all")
//    public Result getProducts(@RequestParam("page") int page,
//                              @RequestParam("size") int size) {
//        return productService.getProducts(page, size);
//    }

    /**
     * 根据目录id和产品名称获取产品
     *
     * @param categoryId
     * @param productName
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/query")
    public Result getProductsByIdName(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                                      @RequestParam(value = "productName", required = false) String productName,
                                      @RequestParam("page") int page,
                                      @RequestParam("size") int size,
                                      @RequestParam("newFlag") Integer newFlag) {
        if (Objects.isNull(categoryId) && Objects.isNull(productName)) {
            return productService.getProducts(page, size, newFlag);
        } else if (Objects.isNull(categoryId) && !Objects.isNull(productName)) {
            return productService.getProductsByName(productName, page, size, newFlag);
        } else if (Objects.isNull(productName) && !Objects.isNull(categoryId)) {
            return productService.getProductsById(categoryId, page, size, newFlag);
        } else {
            return productService.getProductsByIdName(categoryId, productName, page, size, newFlag);
        }
    }


    /**
     * 获取商品详情
     *
     * @param productId
     * @return
     */
    @GetMapping("{id}")
    @Transactional
    public Result getProductDetail(@PathVariable("id") int productId) {
        Map<String, Object> res = new HashMap<>();
        try {
            Product product = productService.getProductDetail(productId);
            List<String> productDetails = productDetailService.getProductDetails(productId);
            List<Map<String,String>> productSpecs = productSpecService.getProductSpec(productId);
            List<Map<String,String>> productOptions = productOptionsService.getProductOptions(productId);
            res.put("id", product.getId());
            res.put("name", product.getProductName());
            res.put("categoryId", product.getCategoryId());
            res.put("lowPrice", product.getProductLowPrice());
            res.put("highPrice", product.getProductHighPrice());
            res.put("description", product.getProductIntro());
            res.put("avatar", product.getProductAvatar());
            res.put("images", productDetails);
            res.put("specs", productSpecs);
            res.put("options", productOptions);
            return Result.build(res, ResultCodeEnum.GET_PRODUCT_DETAIL_SUCCESS);
        } catch (Exception e) {
            log.error("get ProductDetails error", e);
            return Result.build(null, ResultCodeEnum.GET_PRODUCT_DETAIL_ERROR);
        }
    }

    /**
     * 创建商品
     *
     * @param categoryId
     * @param productName
     * @param productLowPrice
     * @param productHighPrice
     * @param productIntro
     * @param image
     * @return
     */
    @PostMapping
    public Result createProduct(@RequestParam("categoryId") Integer categoryId,
                                @RequestParam("productName") String productName,
                                @RequestParam("productLowPrice") Double productLowPrice,
                                @RequestParam("productHighPrice") Double productHighPrice,
                                @RequestParam("productIntro") String productIntro,
                                @RequestParam("image") MultipartFile image) {
        Product product = new Product();
        product.setCategoryId(categoryId);
        product.setProductName(productName);
        product.setProductLowPrice(BigDecimal.valueOf(productLowPrice));
        product.setProductHighPrice(BigDecimal.valueOf(productHighPrice));
        product.setProductIntro(productIntro);

        if (image.isEmpty()) {
            return Result.build(null, 50009, "上传图片为空");
        }
        try {
            byte[] bytes = image.getBytes();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            UUID uuid = UUID.randomUUID();
            String fileName = timestamp.getTime() + "-avatar-product-" + uuid + ".png";
            SSHUtils.sftp(bytes, fileName, "images");
            product.setProductAvatar(StaticParamsCommon.IMAGES_VIDEOS_PATH + fileName);
        } catch (Exception e) {
            log.error("getVideos error", e);
            return Result.build(null, 50009, "图片传输错误");
        }
        return productService.createProduct(product);
    }

    /**
     * 更新商品
     *
     * @param productId
     * @param productName
     * @param productLowPrice
     * @param productHighPrice
     * @param productIntro
     * @param image
     * @return
     */
    @PutMapping
    public Result updateProduct(@RequestParam("productId") Integer productId,
                                @RequestParam("categoryId") Integer categoryId,
                                @RequestParam("productName") String productName,
                                @RequestParam("productLowPrice") Double productLowPrice,
                                @RequestParam("productHighPrice") Double productHighPrice,
                                @RequestParam("productIntro") String productIntro,
                                @RequestPart(required = false) MultipartFile image) {
        Product product = new Product();
        product.setId(productId);
        product.setCategoryId(categoryId);
        product.setProductName(productName);
        product.setProductLowPrice(BigDecimal.valueOf(productLowPrice));
        product.setProductHighPrice(BigDecimal.valueOf(productHighPrice));
        product.setProductIntro(productIntro);
        Boolean deleteFlag = false;
        if (!Objects.isNull(image) && !image.isEmpty()) {
            deleteFlag = true;
            try {
                byte[] bytes = image.getBytes();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                UUID uuid = UUID.randomUUID();
                String fileName = timestamp.getTime() + "-avatar-product-" + uuid + ".png";
                SSHUtils.sftp(bytes, fileName, "images");
                product.setProductAvatar(StaticParamsCommon.IMAGES_VIDEOS_PATH + fileName);
            } catch (Exception e) {
                log.error("getVideos error", e);
                return Result.build(null, 50010, "图片传输错误");
            }
        }
        return productService.updateProduct(product,deleteFlag);
    }

    /**
     * 删除商品
     */
    @Transactional
    @DeleteMapping("{id}")
    public Result deleteProduct(@PathVariable("id") int productId) {
        productDetailService.deleteProductDetailBatch(productId);
        productSpecService.deleteProductSpecBatch(productId);
        productOptionsService.deleteProductOptionsBatch(productId);
        return productService.deleteProduct(productId);
    }

    /**
     * 批量上传商品图片
     *
     * @param productId
     * @param images
     * @return
     */
    @PostMapping("/image")
    public Result createProductDetails(@RequestParam("productId") Integer productId,
                                       @RequestParam("images") List<MultipartFile> images) {
        List<ProductDetail> productDetails = new ArrayList<>();
        for (MultipartFile image : images) {
            if (image.isEmpty()) {
                return Result.build(null, 50012, "上传图片为空");
            }
            try {
                byte[] bytes = image.getBytes();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                String fileName = timestamp.getTime() + "-product" + productId + ".png";
                SSHUtils.sftp(bytes, fileName, "images");

                ProductDetail productDetail = new ProductDetail();
                productDetail.setProductDetailName(fileName);
                productDetail.setProductId(productId);
                productDetail.setProductDetail(StaticParamsCommon.IMAGES_VIDEOS_PATH + fileName);
                productDetails.add(productDetail);
            } catch (Exception e) {
                log.error("getVideos error", e);
                return Result.build(null, 50012, "图片传输错误");
            }
        }
        return productDetailService.uploadProductDetails(productDetails);
    }

    /**
     * 删除商品图片
     */
    @DeleteMapping("/image")
    public Result deleteProductDetail(@RequestBody Map<String, Object> data) {
        int productId = (Integer) data.get("productId");
        String imageName = (String) data.get("imageName");
        try {
            SSHUtils.deleteFile(imageName, "images");
        } catch (Exception e) {
            log.error("delete product image error", e);
            return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_IMAGE_ERROR);
        }
        return productDetailService.deleteProductDetail(productId, imageName);
    }


    /**
     * 批量创建商品规格信息
     * @return
     */
    @Transactional
    @PostMapping("/spec")
    public Result createProductSpec(@RequestBody Map<String, List<ProductSpec>> data) {
        List<ProductSpec> productSpecs = data.get("productSpecs");
        //创建商品规格信息
        Result result = productSpecService.createProductSpec(productSpecs);
        //更新商品最高最低价格
        int productId = productSpecs.get(0).getProductId();
        updateProductPrice(productId);
        return result;
    }

    /**
     * 更新商品规格信息
     *
     * @param data
     * @return
     */
    @Transactional
    @PutMapping("/spec")
    public Result updateProductSpec(@RequestBody Map<String, Object> data) {
        ProductSpec productSpec = new ProductSpec();
        productSpec.setProductId((Integer) data.get("productId"));
        productSpec.setProductSpecName((String) data.get("productSpecName"));
        productSpec.setProductSpecValue((String) data.get("productSpecValue"));
        Result result = productSpecService.updateProductSpec(productSpec, (String) data.get("productSpecOldName"));
        //更新商品最高最低价格
        updateProductPrice(productSpec.getProductId());
        return result;
    }

    /**
     * 删除商品规格信息
     *
     * @param data
     * @return
     */
    @Transactional
    @DeleteMapping("/spec")
    public Result deleteProductSpec(@RequestBody Map<String, Object> data) {
        int productId = (Integer) data.get("productId");
        String productSpecName = (String) data.get("productSpecName");
        String productSpecValue = (String) data.get("productSpecValue");
        System.out.println(productSpecValue);
        Result result = productSpecService.deleteProductSpec(productId, productSpecName);
        //更新商品最高最低价格
        updateProductPrice(productId,productSpecValue);
        return result;
    }

    //新建，更新，删除商品自定义选项，写代码

    /**
     * 创建商品自定义选项
     * @return
     */
    @PostMapping("/option")
    public Result createProductOptions(@RequestBody  Map<String,List<ProductOptions>> data) {
        List<ProductOptions> productOptions = data.get("productOptions");
        return productOptionsService.createProductOptions(productOptions);
    }

    /**
     * 更新商品自定义选项
     *
     * @param data
     * @return
     */
    @PutMapping("/option")
    public Result updateProductOptions(@RequestBody Map<String, Object> data) {
        ProductOptions productOptions = new ProductOptions();
        productOptions.setProductId((Integer) data.get("productId"));
        productOptions.setProductOptionName((String) data.get("productOptionName"));
        productOptions.setProductOptionInfo((String) data.get("productOptionInfo"));
        return productOptionsService.updateProductOptions(productOptions, (String) data.get("productOptionOldName"));
    }

    /**
     * 删除商品自定义选项
     *
     * @param data
     * @return
     */
    @DeleteMapping("/option")
    public Result deleteProductOptions(@RequestBody Map<String, Object> data) {
        int productId = (Integer) data.get("productId");
        String productOptionName = (String) data.get("productOptionName");
        return productOptionsService.deleteProductOptions(productId, productOptionName);
    }

    /**
     * 更新商品最高最低价格
     *
     * @param productId
     */
    @Transactional
    public void updateProductPrice(int productId, String... value) {
        Map<String, String> maxMinSpec = productSpecService.getMaxMinSpec(productId);
        Product product = new Product();
        product.setId(productId);
        if (maxMinSpec != null) {
            product.setProductLowPrice(new BigDecimal(maxMinSpec.get("min")));
            product.setProductHighPrice(new BigDecimal(maxMinSpec.get("max")));
        }else {
            System.out.println(value[0]);
            product.setProductLowPrice(new BigDecimal(value[0]));
            product.setProductHighPrice(new BigDecimal(value[0]));
        }
        productService.updateProduct(product, false);
    }

}
