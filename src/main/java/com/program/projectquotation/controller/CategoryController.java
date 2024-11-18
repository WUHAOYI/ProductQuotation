package com.program.projectquotation.controller;

import com.program.projectquotation.pojo.Category;
import com.program.projectquotation.pojo.Product;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by WHY on 2024/9/27.
 * Functions:
 */
@RestController
@RequestMapping("category")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private ProductSpecService productSpecService;

    @Autowired
    private ProductOptionsService productOptionsService;

    /**
     * 获取所有商品目录
     *
     * @return
     */
    @GetMapping()
    public Result getAllCategories() {
        return categoryService.getAllCategories();
    }

    /**
     * 创建商品目录
     *
     * @param data
     * @return
     */
    @PostMapping()
    public Result createCategory(@RequestBody Map<String, Object> data) {
        Category category = new Category();
        category.setCategoryName((String) data.get("name"));
        category.setCategoryLevel((Integer) data.get("level"));
        category.setSuperiorCategoryId((Integer) data.get("superiorId"));
        return categoryService.createCategory(category);
    }

    /**
     * 更新商品目录
     *
     * @param data
     * @return
     */
    @PutMapping()
    public Result updateCategory(@RequestBody Map<String, Object> data) {
        Category category = new Category();
        category.setId((Integer) data.get("id"));
        category.setCategoryName((String) data.get("name"));
        category.setCategoryLevel((Integer) data.get("level"));
        category.setSuperiorCategoryId((Integer) data.get("superiorId"));
        return categoryService.updateCategory(category);
    }

    /**
     * 删除商品目录
     * 删除目录会删除该目录下的所有商品
     *
     * @param id
     * @return
     */
    @Transactional
    @DeleteMapping("/{id}")
    public Result deleteCategory(@PathVariable Integer id) {
        List<Category> categories = categoryService.getCategoriesById(id);
        for (Category category : categories) {
            System.out.println(category);
            //只有三级目录下面才有商品
            if (category.getCategoryLevel() == 3) {
                int categoryId = category.getId();
                //获取该目录下的所有商品
                List<Product> products = productService.getProductsByCategoryId(categoryId);
                if (products != null && !products.isEmpty()) {
                    for (Product product : products) {
                        System.out.println("delete product");
                        //根据id删除所有商品
                        int productId = product.getId();
                        productDetailService.deleteProductDetailBatch(productId);
                        productSpecService.deleteProductSpecBatch(productId);
                        productOptionsService.deleteProductOptionsBatch(productId);
                        productService.deleteProduct(productId);
                    }
                }
            }
        }
        for (Category category : categories) {
            if (category.getId() != id) {
                categoryService.deleteCategory(category.getId());
            }
        }
        return categoryService.deleteCategory(id);
    }

}
