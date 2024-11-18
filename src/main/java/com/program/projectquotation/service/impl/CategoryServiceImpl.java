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
        wrapper.select(Category::getId, Category::getCategoryName, Category::getCategoryLevel)
                .eq(Category::getCategoryLevel, 1);
        try {
            // 获取一级目录
            List<Category> categories = categoryMapper.selectList(wrapper);
            List<CategoryDTO> categoryDTOs = categories.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            // 设置子目录
            for (CategoryDTO categoryDTO : categoryDTOs) {
                List<CategoryDTO> childrenDTO = getChildrenDTO(categoryDTO.getId());
                if (!childrenDTO.isEmpty()) {
                    categoryDTO.setChildren(childrenDTO);
                }
            }
            return Result.build(categoryDTOs, ResultCodeEnum.GET_CATEGORY_SUCCESS);
        } catch (Exception e) {
            log.error("get category failed", e);
            return Result.build(null, ResultCodeEnum.GET_CATEGORY_ERROR);
        }
    }

    /**
     * 根据目录id获取所有的子目录
     * @param id
     * @return
     */
    @Override
    public List<Category> getCategoriesById(int id) {
        List<Category> res = new ArrayList<>();
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getId, id);
        Category thisCategory = categoryMapper.selectOne(wrapper);
        res.add(thisCategory);
        wrapper.clear();
        // 设置查询条件，查询子目录
        wrapper.eq(Category::getSuperiorCategoryId, id);
        List<Category> categories = categoryMapper.selectList(wrapper);
        res.addAll(categories);
        for (Category category : categories) {
            res.addAll(getCategoriesById(category.getId()));
        }
        return res;
    }

    /**
     * 创建商品目录
     * @param category
     * @return
     */
    @Override
    public Result createCategory(Category category) {
        try {
            categoryMapper.insert(category);
            return Result.build(null, ResultCodeEnum.CREATE_CATEGORY_SUCCESS);
        } catch (Exception e) {
            log.error("create category failed", e);
            return Result.build(null, ResultCodeEnum.CREATE_CATEGORY_ERROR);
        }
    }

    /**
     * 更新商品目录
     * @param category
     * @return
     */
    @Override
    public Result updateCategory(Category category) {
        try {
            int update = categoryMapper.updateById(category);
            if (update == 0) {
                return Result.build(null, ResultCodeEnum.UPDATE_CATEGORY_ERROR);
            }
            return Result.build(null, ResultCodeEnum.UPDATE_CATEGORY_SUCCESS);
        } catch (Exception e) {
            log.error("update category failed", e);
            return Result.build(null, ResultCodeEnum.UPDATE_CATEGORY_ERROR);
        }
    }

    /**
     * 删除商品目录
     * @param id
     * @return
     */
    @Override
    public Result deleteCategory(Integer id) {
        try {
            int delete = categoryMapper.deleteById(id);
            if (delete == 0) {
                return Result.build(null, ResultCodeEnum.DELETE_CATEGORY_ERROR);
            }
            return Result.build(null, ResultCodeEnum.DELETE_CATEGORY_SUCCESS);
        } catch (Exception e) {
            log.error("delete category failed", e);
            return Result.build(null, ResultCodeEnum.DELETE_CATEGORY_ERROR);
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
                .select(Category::getId, Category::getCategoryName, Category::getCategoryLevel);
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
        dto.setCategoryLevel(category.getCategoryLevel());
        return dto;
    }
}




