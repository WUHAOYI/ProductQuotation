package com.program.projectquotation.mapper;

import com.program.projectquotation.pojo.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Administrator
* @description 针对表【category(商品目录)】的数据库操作Mapper
* @createDate 2024-09-27 18:44:23
* @Entity com.program.projectquotation.pojo.Category
*/
public interface CategoryMapper extends BaseMapper<Category> {
    // 查询所有目录，只返回 id 和 category_name
    List<Category> selectCategoryBasicInfo();

    // 查询某个 superiorCategoryId 下的子目录
    List<Category> selectChildrenCategories(Integer superiorCategoryId);
}




