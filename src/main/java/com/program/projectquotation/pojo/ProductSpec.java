package com.program.projectquotation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品规格表
 * @TableName product_spec
 */
@TableName(value ="product_spec")
@Data
public class ProductSpec implements Serializable {
    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品规格名称
     */
    private String productSpecName;

    /**
     * 商品规格值
     */
    private String productSpecValue;

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