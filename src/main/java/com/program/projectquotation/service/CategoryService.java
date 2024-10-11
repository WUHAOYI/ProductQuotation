package com.program.projectquotation.service;

import com.program.projectquotation.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.program.projectquotation.result.Result;

/**
* @author Administrator
* @description 针对表【category(商品目录)】的数据库操作Service
* @createDate 2024-09-27 18:44:24
*/
public interface CategoryService extends IService<Category> {
    public Result getAllCategories();

    public Result createCategory(Category category);

    public Result updateCategory(Category category);

    public Result deleteCategory(Integer id);
}
