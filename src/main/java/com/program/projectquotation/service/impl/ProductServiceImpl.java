package com.program.projectquotation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.program.projectquotation.pojo.Product;
import com.program.projectquotation.pojo.Video;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.result.ResultCodeEnum;
import com.program.projectquotation.service.NewdateuntilnowService;
import com.program.projectquotation.service.ProductService;
import com.program.projectquotation.mapper.ProductMapper;
import com.program.projectquotation.utils.SSHUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Administrator
 * @description 针对表【product(商品表)】的数据库操作Service实现
 * @createDate 2024-10-01 16:37:30
 */
@Service
@Transactional
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
        implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private NewdateuntilnowService newdateuntilnowService;

    /**
     * 获取所有商品
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Result getProducts(int page, int size, int newFlag) {
        try {
            Page<Product> thisPage = new Page<>(page, size);
            LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByDesc(Product::getCreateTime);
            if (newFlag == 1) {
                LocalDate today = LocalDate.now();
                int minus = newdateuntilnowService.getNewdateuntilnow();
                LocalDate newDate = today.minusDays(minus);
                Date date = Date.valueOf(newDate);
                wrapper.gt(Product::getCreateTime, date);
            }
            Page<Product> productPage = productMapper.selectPage(thisPage, wrapper);
            Map<String, Object> res = new HashMap<>();
            long total = productMapper.selectCount(null);
            List<Product> products = productPage.getRecords();
            res.put("products", products);
            res.put("total", total);
            if (Objects.isNull(products) || products.isEmpty()) {
                return Result.build(null, ResultCodeEnum.PRODUCT_NOT_FOUND);
            }
            return Result.build(res, ResultCodeEnum.GET_PRODUCT_SUCCESS);
        } catch (Exception e) {
            log.error("get Products error", e);
            return Result.build(null, ResultCodeEnum.GET_PRODUCT_ERROR);
        }
    }

    /**
     * 根据目录id获取所有商品（不分页）
     *
     * @param categoryId
     * @return
     */
    @Override
    public List<Product> getProductsByCategoryId(int categoryId) {
        try {
            LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Product::getCategoryId, categoryId);
            List<Product> products = productMapper.selectList(wrapper);
            if (Objects.isNull(products) || products.isEmpty()) {
                return null;
            }
            return products;
        } catch (Exception e) {
            log.error("get all Products(without page) error", e);
            return null;
        }
    }

    /**
     * 根据目录id获取商品
     *
     * @param categoryId
     * @param page
     * @param size
     * @return
     */
    @Override
    public Result getProductsById(int categoryId, int page, int size, int newFlag) {
        try {
            LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByDesc(Product::getCreateTime);
            if (newFlag == 1) {
                LocalDate today = LocalDate.now();
                int minus = newdateuntilnowService.getNewdateuntilnow();
                LocalDate newDate = today.minusDays(minus);
                Date date = Date.valueOf(newDate);
                wrapper.gt(Product::getCreateTime, date);
            }
            Page<Product> thisPage = new Page<>(page, size);
            wrapper.eq(Product::getCategoryId, categoryId);
            Page<Product> productPage = productMapper.selectPage(thisPage, wrapper);
            Map<String, Object> res = new HashMap<>();
            long total = productMapper.selectCount(null);
            List<Product> products = productPage.getRecords();
            res.put("products", products);
            res.put("total", total);

            if (Objects.isNull(products) || products.isEmpty()) {
                return Result.build(null, ResultCodeEnum.PRODUCT_NOT_FOUND);
            }
            return Result.build(res, ResultCodeEnum.GET_PRODUCT_SUCCESS);
        } catch (Exception e) {
            log.error("get Products error", e);
            return Result.build(null, ResultCodeEnum.GET_PRODUCT_ERROR);
        }
    }

    /**
     * 根据商品名称获取商品
     *
     * @param productName
     * @param page
     * @param size
     * @return
     */
    @Override
    public Result getProductsByName(String productName, int page, int size, int newFlag) {
        try {
            LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByDesc(Product::getCreateTime);
            if (newFlag == 1) {
                LocalDate today = LocalDate.now();
                int minus = newdateuntilnowService.getNewdateuntilnow();
                LocalDate newDate = today.minusDays(minus);
                Date date = Date.valueOf(newDate);
                wrapper.gt(Product::getCreateTime, date);
            }
            Page<Product> thisPage = new Page<>(page, size);
            wrapper.like(Product::getProductName, productName);
            Page<Product> productPage = productMapper.selectPage(thisPage, wrapper);
            Map<String, Object> res = new HashMap<>();
            long total = productMapper.selectCount(null);
            List<Product> products = productPage.getRecords();
            res.put("products", products);
            res.put("total", total);
            if (Objects.isNull(products) || products.isEmpty()) {
                return Result.build(null, ResultCodeEnum.PRODUCT_NOT_FOUND);
            }
            return Result.build(res, ResultCodeEnum.GET_PRODUCT_SUCCESS);
        } catch (Exception e) {
            log.error("get Products error", e);
            return Result.build(null, ResultCodeEnum.GET_PRODUCT_ERROR);
        }
    }

    /**
     * 根据目录id和商品名称获取商品
     */
    @Override
    public Result getProductsByIdName(int categoryId, String productName, int page, int size, int newFlag) {
        try {
            LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByDesc(Product::getCreateTime);
            if (newFlag == 1) {
                LocalDate today = LocalDate.now();
                int minus = newdateuntilnowService.getNewdateuntilnow();
                LocalDate newDate = today.minusDays(minus);
                Date date = Date.valueOf(newDate);
                wrapper.gt(Product::getCreateTime, date);
            }
            Page<Product> thisPage = new Page<>(page, size);
            wrapper.eq(Product::getCategoryId, categoryId).like(Product::getProductName, productName);
            Page<Product> productPage = productMapper.selectPage(thisPage, wrapper);
            Map<String, Object> res = new HashMap<>();
            long total = productMapper.selectCount(null);
            List<Product> products = productPage.getRecords();
            res.put("products", products);
            res.put("total", total);
            if (Objects.isNull(products) || products.isEmpty()) {
                return Result.build(null, ResultCodeEnum.PRODUCT_NOT_FOUND);
            }
            return Result.build(res, ResultCodeEnum.GET_PRODUCT_SUCCESS);
        } catch (Exception e) {
            log.error("get Products error", e);
            return Result.build(null, ResultCodeEnum.GET_PRODUCT_ERROR);
        }
    }

    /**
     * 获取商品详情
     *
     * @param productId
     * @return
     */
    @Override
    public Product getProductDetail(int productId) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getId, productId);
        try {
            Product product = productMapper.selectOne(wrapper);
            if (Objects.isNull(product)) {
                return null;
            }
            return product;
        } catch (Exception e) {
            log.error("get ProductDetail error", e);
            return null;
        }
    }

    /**
     * 创建商品
     *
     * @param product
     * @return
     */
    @Override
    public Result createProduct(Product product) {
        try {
            int insert = productMapper.insert(product);
            if (insert == 0) {
                return Result.build(null, ResultCodeEnum.CREATE_PRODUCT_ERROR);
            }
            Map<String, Integer> data = Map.of("productId", product.getId());
            return Result.build(data, ResultCodeEnum.CREATE_PRODUCT_SUCCESS);
        } catch (Exception e) {
            log.error("create Product error", e);
            return Result.build(null, ResultCodeEnum.CREATE_PRODUCT_ERROR);
        }
    }

    /**
     * 更新商品
     *
     * @param product
     * @return
     */
    @Override
    public Result updateProduct(Product product, Boolean deleteFlag) {
        if (deleteFlag) {
            try {
                deleteAvatarFile(product.getId());
            } catch (Exception e) {
                return Result.build(null, 50010, "无法删除历史图片信息");
            }
        }

        try {
            int update = productMapper.updateById(product);
            if (update == 0) {
                return Result.build(null, ResultCodeEnum.PRODUCT_NOT_FOUND);
            }
            return Result.build(null, ResultCodeEnum.UPDATE_PRODUCT_SUCCESS);
        } catch (Exception e) {
            log.error("update Product error", e);
            return Result.build(null, ResultCodeEnum.UPDATE_PRODUCT_ERROR);
        }
    }

    /**
     * 删除商品
     *
     * @param productId
     * @return
     */
    @Override
    public Result deleteProduct(int productId) {
        try {
            deleteAvatarFile(productId);
        } catch (Exception e) {
            return Result.build(null, 50011, "无法删除历史图片信息");
        }

        try {
            productMapper.deleteById(productId);
            System.out.println("delete product success");
            return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_SUCCESS);
        } catch (Exception e) {
            log.error("delete Product error", e);
            return Result.build(null, ResultCodeEnum.DELETE_PRODUCT_ERROR);
        }
    }


    private void deleteAvatarFile(int productId) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<Product>().eq(Product::getId, productId);
        Product selected = productMapper.selectOne(wrapper);
        String productAvatar = selected.getProductAvatar();
        String name = productAvatar.split("//")[1].split("/")[1];
        try {
            SSHUtils.deleteFile(name, "images");
        } catch (Exception e) {
            log.error("delete avatar error", e);
        }
    }
}




