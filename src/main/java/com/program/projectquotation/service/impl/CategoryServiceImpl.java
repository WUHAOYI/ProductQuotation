package com.program.projectquotation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.program.projectquotation.pojo.Category;
import com.program.projectquotation.pojo.DTO.CategoryDTO;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.result.ResultCodeEnum;
import com.program.projectquotation.service.CategoryService;
import com.program.projectquotation.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【category(商品目录)】的数据库操作Service实现
 * @createDate 2024-09-27 18:44:23
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获取所有商品目录
     *
     * @return
     */
    public Result getAllCategories() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        // 设置查询条件，查询一级目录
        wrapper.select(Category::getId, Category::getCategoryName)
                .eq(Category::getCategoryLevel, 1);
        try {
            // 获取一级目录
            List<Category> categories = categoryMapper.selectList(wrapper);
            List<CategoryDTO> categoryDTOs = categories.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            // 设置子目录
            for (CategoryDTO categoryDTO : categoryDTOs) {
                categoryDTO.setChildren(getChildrenDTO(categoryDTO.getId()));
            }
            return Result.build(categoryDTOs, ResultCodeEnum.GET_CATEGORY_SUCCESS);
        } catch (Exception e) {
            log.error("获取商品目录失败", e);
            return Result.build(null, ResultCodeEnum.GET_CATEGORY_ERROR);
        }
    }

    /**
     * 获取子目录
     *
     * @param superiorCategoryId
     * @return
     */
    private List<CategoryDTO> getChildrenDTO(Integer superiorCategoryId) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        // 设置查询条件，查询子目录
        wrapper.eq(Category::getSuperiorCategoryId, superiorCategoryId)
                .select(Category::getId, Category::getCategoryName);
        //  获取子目录
        List<Category> subCategories = categoryMapper.selectList(wrapper);
        List<CategoryDTO> categoryDTOS = subCategories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        //设置子目录
        for (CategoryDTO categoryDTO : categoryDTOS) {
            // 注意：这里还需查询一次三级目录
            List<CategoryDTO> childrenDTO = getChildrenDTO(categoryDTO.getId());
            if (!childrenDTO.isEmpty()) {
                categoryDTO.setChildren(childrenDTO);
            }
        }
        return categoryDTOS;
    }

    /**
     * 将Category对象转换为CategoryDTO对象
     *
     * @param category
     * @return
     */
    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }
}




