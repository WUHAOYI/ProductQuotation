package com.program.projectquotation.controller;

import com.program.projectquotation.pojo.Category;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 获取所有商品目录
     * @return
     */
    @GetMapping()
    public Result getAllCategories() {
        return categoryService.getAllCategories();
    }

    /**
     * 创建商品目录
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
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteCategory(@PathVariable Integer id) {
        return categoryService.deleteCategory(id);
    }
}
