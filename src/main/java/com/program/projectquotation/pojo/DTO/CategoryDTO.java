package com.program.projectquotation.pojo.DTO;

import com.program.projectquotation.pojo.Category;
import lombok.Data;

import java.util.List;

/**
 * Created by WHY on 2024/9/27.
 * Functions:
 */
@Data
public class CategoryDTO {
    private Integer id;
    private String categoryName;
    private List<CategoryDTO> children;
}
