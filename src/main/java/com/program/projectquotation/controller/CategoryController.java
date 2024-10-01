package com.program.projectquotation.controller;

import com.program.projectquotation.result.Result;
import com.program.projectquotation.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping()
    public Result getAllCategories() {
        return categoryService.getAllCategories();
    }
}
