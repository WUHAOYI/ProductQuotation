package com.program.projectquotation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品自定义选项
 * @TableName product_options
 */
@TableName(value ="product_options")
@Data
public class ProductOptions implements Serializable {
    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品自定义选项名称
     */
    private String productOptionName;

    /**
     * 商品自定义选项信息
     */
    private String productOptionInfo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}